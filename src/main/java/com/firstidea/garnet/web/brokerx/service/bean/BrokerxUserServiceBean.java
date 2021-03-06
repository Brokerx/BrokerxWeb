/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.firstidea.garnet.web.brokerx.service.bean;

import com.firstidea.garnet.web.brokerx.admin.ApplicationUser;
import com.firstidea.garnet.web.brokerx.constants.QueryConstants;
import com.firstidea.garnet.web.brokerx.dto.DashboardDTO;
import com.firstidea.garnet.web.brokerx.dto.DropDownValuesDTO;
import com.firstidea.garnet.web.brokerx.dto.MessageDTO;
import com.firstidea.garnet.web.brokerx.dto.UserDTO;
import com.firstidea.garnet.web.brokerx.entity.Lead;
import com.firstidea.garnet.web.brokerx.entity.User;
import com.firstidea.garnet.web.brokerx.entity.UserConnection;
import com.firstidea.garnet.web.brokerx.enums.ConnectionStatus;
import com.firstidea.garnet.web.brokerx.enums.LeadCurrentStatus;
import com.firstidea.garnet.web.brokerx.filehandling.FileUploadHelper;
import com.firstidea.garnet.web.brokerx.service.BrokerxUserService;
import com.firstidea.garnet.web.brokerx.util.ApptDateUtils;
import com.firstidea.garnet.web.brokerx.util.GCMUtils;
import com.firstidea.garnet.web.brokerx.util.GarnetStringUtils;
import com.firstidea.garnet.web.brokerx.util.JsonConverter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Govind
 */
@Stateless
public class BrokerxUserServiceBean implements BrokerxUserService {

    static final Logger logger = LoggerFactory.getLogger(BrokerxUserServiceBean.class);

    @PersistenceContext
    EntityManager em;
    private ApplicationUser sessionUser = null;

    private void setCurrentUser(ApplicationUser user) {
        this.sessionUser = user;
    }

    @Override
    public ApplicationUser getCurrentUser() {
        return sessionUser;
    }

    @Override
    public MessageDTO registerUser(User user, Map<String, FileItem> fileItemsMap) {
        try {
            if (user.getUserID() == null) {
                // TODO check for existing user by email and mobile and send error accordingly
                Query mobileCountQuery = em.createQuery(QueryConstants.GET_USER_COUNT_BY_MOBILE)
                        .setParameter("mobile", user.getMobile());
                Long userCount = (Long) mobileCountQuery.getSingleResult();
                if (userCount > 0) {
                    MessageDTO messageDTO = MessageDTO.getFailureDTO();
                    messageDTO.setMessageText("Mobile number already exist");
                    return messageDTO;
                }

                Query emailCountQuery = em.createQuery(QueryConstants.GET_USER_COUNT_BY_EMAIL)
                        .setParameter("email", user.getEmail());
                Long emailUserCount = (Long) emailCountQuery.getSingleResult();
                if (emailUserCount > 0) {
                    MessageDTO messageDTO = MessageDTO.getFailureDTO();
                    messageDTO.setMessageText("Email ID already exist");
                    return messageDTO;
                }
            }
            String fileNames = "";
            for (String fileName : fileItemsMap.keySet()) {
                String uploadPath[] = FileUploadHelper.getPathToUploadFile(fileName, FileUploadHelper.FILTE_TYPE_USER_PROFILE_PHOTO);
                if (uploadPath != null) {
                    boolean isFileUploaded = FileUploadHelper.UploadImage(fileItemsMap.get(fileName), uploadPath[0]);
                    if (isFileUploaded) {
                        String regex;
                        if (FileUploadHelper.fileSeparator.equals("\\")) {
                            regex = "\\\\";
                        } else {
                            regex = "/";
                        }
                        String path[] = uploadPath[0].split(regex);
                        String uploadedFileName = path[path.length - 1];
                        if (uploadedFileName.toLowerCase().endsWith(".jpg") || uploadedFileName.toLowerCase().endsWith(".jpeg")
                                || uploadedFileName.toLowerCase().endsWith(".png")) {
                            String thumbNailImagePath = uploadPath[0].substring(0, uploadPath[0].lastIndexOf(FileUploadHelper.fileSeparator));
                            FileUploadHelper.createThumbNailForUploadedImage(uploadPath[0], uploadedFileName, thumbNailImagePath, true, null, null);
                        }
                    }
                    fileNames += uploadPath[1];
                }

            }
            if (user == null) {
                return MessageDTO.getFailureDTO();
            }
            if (StringUtils.isNotBlank(fileNames)) {
                user.setImageURL(fileNames);
            }
            if (user.getUserID() != null) {
                User prevUser = em.find(User.class, user.getUserID());
                user.setPassword(prevUser.getPassword());
                if (StringUtils.isBlank(fileNames)) {
                    user.setImageURL(prevUser.getImageURL());
                }
                if (StringUtils.isBlank(prevUser.getGcmKey())) {
                    user.setGcmKey(prevUser.getGcmKey());
                }
                em.merge(user);
            } else {

                em.persist(user);
            }
//            updateUserOTPTable(user.getUserID(), user.getMobile());
            sessionUser = new ApplicationUser();
            sessionUser.setUserId(user.getUserID());
            sessionUser.setEmail(user.getEmail());
            sessionUser.setName(user.getFullName());
            setCurrentUser(sessionUser);
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(user);

            return messageDTO;
        } catch (Exception e) {
            logger.error(BrokerxUserServiceBean.class + " registerUser() : ERROR " + e.toString());
            MessageDTO messageDTO = MessageDTO.getFailureDTO();
            messageDTO.setMessageText("Server Error, Please contact Administrator");
            return messageDTO;
        }
    }

    @Override
    public MessageDTO appLogin(String userName, String Password) {
        try {
            Query q = em.createQuery(QueryConstants.GET_LOGIN_USER)
                    .setParameter("userID", userName)
                    .setParameter("password", Password);

            User user = (User) q.getSingleResult();
            MessageDTO messageDTO;
            if (user != null) {
                messageDTO = MessageDTO.getSuccessDTO();
                messageDTO.setData(user);
            } else {
                messageDTO = MessageDTO.getFailureDTO();
            }
            return messageDTO;
        } catch (Exception e) {
            logger.error(BrokerxUserServiceBean.class + " appLogin() : ERROR " + e.toString());
            return MessageDTO.getFailureDTO();
        }
    }

    @Override
    public MessageDTO getUserByMobile(String mobile) {
        try {
            MessageDTO messageDTO;
            Query query = em.createQuery(QueryConstants.GET_USERS_BY_MOBILE)
                    .setParameter("mobile", mobile);
            User user = (User) query.getSingleResult();
            if (user != null) {
                messageDTO = MessageDTO.getSuccessDTO();
                messageDTO.setData(user);
            } else {
                messageDTO = MessageDTO.getFailureDTO();
            }
            return messageDTO;
        } catch (Exception e) {
            logger.error(BrokerxUserServiceBean.class + " getUserByMobile() : ERROR " + e.toString());
            return MessageDTO.getFailureDTO();
        }
    }

    @Override
    public MessageDTO sendConnectionRequest(Integer fromUserID, Integer toUserID) {
        try {
            MessageDTO messageDTO;

            UserConnection userConnection = new UserConnection();
            userConnection.setFromUserID(fromUserID);
            userConnection.setToUserID(toUserID);
            userConnection.setStatus(ConnectionStatus.PENDING.getStatus());
            userConnection.setStatusDttm(ApptDateUtils.getCurrentDateAndTime());
            userConnection.setCreatedDttm(ApptDateUtils.getCurrentDateAndTime());
            em.persist(userConnection);
            messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(userConnection);
            User user = em.find(User.class, toUserID);
            if (StringUtils.isNotBlank(user.getGcmKey())) {
                GCMUtils.sendNotification(user.getGcmKey(), user.getFullName(), GCMUtils.TYPE_CONNECTION_REQUEST, "");
            }
            return messageDTO;
        } catch (Exception e) {
            logger.error(BrokerxUserServiceBean.class + " changeRequestStatus() : ERROR " + e.toString());
            return MessageDTO.getFailureDTO();
        }
    }

    @Override
    public MessageDTO changeConnectionStatus(Integer connectionID, String status) {
        try {
            MessageDTO messageDTO;
            UserConnection userConnection = em.find(UserConnection.class, connectionID);
            if (userConnection != null) {
                userConnection.setStatus(status);
                em.merge(userConnection);
                messageDTO = MessageDTO.getSuccessDTO();
                messageDTO.setData(userConnection);
                User fromUser = em.find(User.class, userConnection.getFromUserID());
                if (StringUtils.isNotBlank(fromUser.getGcmKey())) {
                    User toUser = em.find(User.class, userConnection.getToUserID());
                    String type = status.equals(ConnectionStatus.ACCEPTED) ? GCMUtils.TYPE_CONNECTION_REQUEST_ACCEPTED : GCMUtils.TYPE_CONNECTION_REQUEST_REJECTED;
                    GCMUtils.sendNotification(fromUser.getGcmKey(), toUser.getFullName(), type, "");
                }
            } else {
                messageDTO = MessageDTO.getFailureDTO();
            }
            return messageDTO;
        } catch (Exception e) {
            logger.error(BrokerxUserServiceBean.class + " changeRequestStatus() : ERROR " + e.toString());
            return MessageDTO.getFailureDTO();
        }
    }

    @Override
    public MessageDTO getUserConnections(Integer userID) {
        try {
            MessageDTO messageDTO;
            Query query = em.createQuery(QueryConstants.GET_USERS_ALL_CONNECTIONS)
                    .setParameter("userID", userID);
            List<UserConnection> userConnections = query.getResultList();
            if (userConnections != null) {
                messageDTO = MessageDTO.getSuccessDTO();
                List<User> users = mapConnectionToUserObject(userID, userConnections);
                messageDTO.setData(users);
            } else {
                messageDTO = MessageDTO.getFailureDTO();
            }
            return messageDTO;
        } catch (Exception e) {
            logger.error(BrokerxUserServiceBean.class + " getUserConnections() : ERROR " + e.toString());
            return MessageDTO.getFailureDTO();
        }
    }

    private List<User> mapConnectionToUserObject(Integer userID, List<UserConnection> connections) {
        if (connections == null || (connections != null && connections.isEmpty())) {
            return new ArrayList<User>();
        }
        String userIDs = "";
        Map<Integer, UserConnection> connectionMap = new HashMap<Integer, UserConnection>();
        for (UserConnection connection : connections) {
            if (userID.equals(connection.getFromUserID())) {
                userIDs += connection.getToUserID() + ",";
                connectionMap.put(connection.getToUserID(), connection);
            } else {
                userIDs += connection.getFromUserID() + ",";
                connectionMap.put(connection.getFromUserID(), connection);
            }
        }
//        userIDs += userID;

        List<Integer> userdIdList = GarnetStringUtils.getListOfComaValuesInBigInteger(userIDs);
        Query query = em.createQuery(QueryConstants.GET_USERS_BY_USER_IDS)
                .setParameter("userIDs", userdIdList);
        List<User> users = query.getResultList();
        for (User user : users) {
            UserConnection connection = connectionMap.get(user.getUserID());
            user.setStatus(connection.getStatus());
            if (userID.equals(connection.getFromUserID())) {
                user.setRating(connection.getToUserrating());
                user.setIsMyRequest(true);
            } else {
                user.setRating(connection.getFromUserRating());
            }
            user.setUserConnectionID(connection.getUserConnectionID());
        }

        return users;
    }

    @Override
    public MessageDTO updateGCMKey(Integer userID, String gcmKey) {
        try {
            MessageDTO messageDTO;
            User user = em.find(User.class, userID);
            if (user != null) {
                user.setGcmKey(gcmKey);
                em.merge(user);
                messageDTO = MessageDTO.getSuccessDTO();
                messageDTO.setData(user);
                return messageDTO;
            }
        } catch (Exception e) {
            logger.error(BrokerxUserServiceBean.class + " updateGCMKey() : ERROR " + e.toString());
        }
        return MessageDTO.getFailureDTO();
    }

    @Override
    public MessageDTO updateBrokerDealsInItems(Integer brokerID, String items) {
        try {
            MessageDTO messageDTO;
            User user = em.find(User.class, brokerID);
            if (user != null) {
                user.setBrokerDealsInItems(items);
                em.merge(user);
                messageDTO = MessageDTO.getSuccessDTO();
                messageDTO.setData(user);
                return messageDTO;
            }
        } catch (Exception e) {
            logger.error(BrokerxUserServiceBean.class + " updateBrokerDealsInItems() : ERROR " + e.toString());
        }
        return MessageDTO.getFailureDTO();
    }

    @Override
    public MessageDTO getUsers(String userType, Date startDate, Date endDate) {
        try {
            Boolean isBroker = userType.equals("broker");
            Query query = em.createQuery(QueryConstants.GET_USERS_BY_TYPE)
                    .setParameter("isBroker", isBroker);
            List<User> users = query.getResultList();
            List<Integer> userIDs = new ArrayList<Integer>();
            for (User user : users) {
                userIDs.add(user.getUserID());
            }
            List<Object[]> buyerStatusCounts, sellerStatusCounts;
            if (isBroker) {
                Query buyerStatusQuery = em.createNativeQuery(QueryConstants.GET_BROKERS_BUYER_DEAL_STATUS_COUNT)
                        .setParameter("userIDs", userIDs);
                buyerStatusCounts = buyerStatusQuery.getResultList();

                Query sellerStatusQuery = em.createNativeQuery(QueryConstants.GET_BROKERS_SELLER_DEAL_STATUS_COUNT)
                        .setParameter("userIDs", userIDs);
                sellerStatusCounts = sellerStatusQuery.getResultList();
            } else {
                Query buyerStatusQuery = em.createNativeQuery(QueryConstants.GET_USERS_BUYER_DEAL_STATUS_COUNT)
                        .setParameter("userIDs", userIDs);
                buyerStatusCounts = buyerStatusQuery.getResultList();

                Query sellerStatusQuery = em.createNativeQuery(QueryConstants.GET_USERS_SELLER_DEAL_STATUS_COUNT)
                        .setParameter("userIDs", userIDs);
                sellerStatusCounts = sellerStatusQuery.getResultList();
            }
            Map<Integer, BigDecimal> brokerEarningsMap = new HashMap();
            Map<Integer, BigDecimal> sellerEarningsMap = new HashMap();
            Map<Integer, BigDecimal> buyerSpendingsMap = new HashMap();
            if (isBroker) {
                Query brokerEarningQuery = em.createNativeQuery(QueryConstants.GET_BROKER_EARNINGS);
                List<Object[]> brokerEarnings = brokerEarningQuery.getResultList();
                for (Object[] brokerEarning : brokerEarnings) {
                    Integer userID = (Integer) brokerEarning[0];
                    BigDecimal earning = (BigDecimal) brokerEarning[1];
                    brokerEarningsMap.put(userID, earning);
                }
            } else {
                Query buyerSpendingQuery = em.createNativeQuery(QueryConstants.GET_BUYER_SPENDING);
                List<Object[]> buyerSpendings = buyerSpendingQuery.getResultList();
                for (Object[] buyerSpending : buyerSpendings) {
                    Integer userID = (Integer) buyerSpending[0];
                    BigDecimal earning = (BigDecimal) buyerSpending[1];
                    if (buyerSpendingsMap.containsKey(userID)) {
                        BigDecimal totalEarning = buyerSpendingsMap.get(userID).add(earning);
                        buyerSpendingsMap.put(userID, totalEarning);
                    } else {
                        buyerSpendingsMap.put(userID, earning);
                    }
                }

                Query SellerEarningsQuery = em.createNativeQuery(QueryConstants.GET_SELLER_EARNING);
                List<Object[]> sellerEarnings = SellerEarningsQuery.getResultList();
                for (Object[] sellerEarning : sellerEarnings) {
                    Integer userID = (Integer) sellerEarning[0];
                    BigDecimal earning = (BigDecimal) sellerEarning[1];
                    if (sellerEarningsMap.containsKey(userID)) {
                        BigDecimal totalEarning = sellerEarningsMap.get(userID).add(earning);
                        sellerEarningsMap.put(userID, totalEarning);
                    } else {
                        sellerEarningsMap.put(userID, earning);
                    }
                }
            }
            Map<Integer, Integer> buyerActiveDealsCount = new HashMap();
            Map<Integer, Integer> buyerPendingDealsCount = new HashMap();
            Map<Integer, Integer> buyerDoneDealsCount = new HashMap();
            Map<Integer, Integer> buyerRejectedDealsCount = new HashMap();
            Map<Integer, Integer> sellerActiveDealsCount = new HashMap();
            Map<Integer, Integer> sellerPendingDealsCount = new HashMap();
            Map<Integer, Integer> sellerDoneDealsCount = new HashMap();
            Map<Integer, Integer> sellerRejectedDealsCount = new HashMap();
            for (Object[] buyerStatus : buyerStatusCounts) {
                Integer userID = (Integer) buyerStatus[0];
                String status = buyerStatus[1].toString();
                Integer count = ((BigInteger) buyerStatus[2]).intValue();
                if (status.equals(LeadCurrentStatus.Pending.getStatus())) {
                    buyerPendingDealsCount.put(userID, count);
                } else if (status.equals(LeadCurrentStatus.Rejected.getStatus())) {
                    buyerRejectedDealsCount.put(userID, count);
                } else if (status.equals(LeadCurrentStatus.Done.getStatus())) {
                    buyerDoneDealsCount.put(userID, count);
                } else {
                    buyerActiveDealsCount.put(userID, count);
                }
            }
            for (Object[] sellerStatus : sellerStatusCounts) {
                Integer userID = (Integer) sellerStatus[0];
                String status = sellerStatus[1].toString();
                Integer count = ((BigInteger) sellerStatus[2]).intValue();
                if (status.equals(LeadCurrentStatus.Pending.getStatus())) {
                    sellerPendingDealsCount.put(userID, count);
                } else if (status.equals(LeadCurrentStatus.Rejected.getStatus())) {
                    buyerRejectedDealsCount.put(userID, count);
                } else if (status.equals(LeadCurrentStatus.Done.getStatus())) {
                    sellerDoneDealsCount.put(userID, count);
                } else {
                    sellerActiveDealsCount.put(userID, count);
                }
            }
            List<UserDTO> userDTOs = new ArrayList<UserDTO>(users.size());
            for (User user : users) {
                UserDTO userDTO = new UserDTO();
                userDTO.setUserInfo(user);
                if (brokerEarningsMap.containsKey(user.getUserID())) {
                    userDTO.setBrokerEarnings(brokerEarningsMap.get(user.getUserID()));
                } else {
                    userDTO.setBrokerEarnings(BigDecimal.ZERO);
                }
                if (buyerSpendingsMap.containsKey(user.getUserID())) {
                    userDTO.setBuyerspendings(buyerSpendingsMap.get(user.getUserID()));
                } else {
                    userDTO.setBuyerspendings(BigDecimal.ZERO);
                }
                if (sellerEarningsMap.containsKey(user.getUserID())) {
                    userDTO.setSellerEarnings(sellerEarningsMap.get(user.getUserID()));
                } else {
                    userDTO.setSellerEarnings(BigDecimal.ZERO);
                }
                if (buyerActiveDealsCount.containsKey(user.getUserID())) {
                    userDTO.setBuyerActiveDealsCount(buyerActiveDealsCount.get(user.getUserID()));
                } else {
                    userDTO.setBuyerActiveDealsCount(0);
                }
                if (buyerPendingDealsCount.containsKey(user.getUserID())) {
                    userDTO.setBuyerPendingDealsCount(buyerPendingDealsCount.get(user.getUserID()));
                } else {
                    userDTO.setBuyerPendingDealsCount(0);
                }
                if (buyerDoneDealsCount.containsKey(user.getUserID())) {
                    userDTO.setBuyerDoneDealsCount(buyerDoneDealsCount.get(user.getUserID()));
                } else {
                    userDTO.setBuyerDoneDealsCount(0);
                }
                if (buyerRejectedDealsCount.containsKey(user.getUserID())) {
                    userDTO.setBuyerRejectedDealsCount(buyerRejectedDealsCount.get(user.getUserID()));
                } else {
                    userDTO.setBuyerRejectedDealsCount(0);
                }
                if (sellerActiveDealsCount.containsKey(user.getUserID())) {
                    userDTO.setSellerActiveDealsCount(sellerActiveDealsCount.get(user.getUserID()));
                } else {
                    userDTO.setSellerActiveDealsCount(0);
                }
                if (sellerPendingDealsCount.containsKey(user.getUserID())) {
                    userDTO.setSellerPendingDealsCount(sellerPendingDealsCount.get(user.getUserID()));
                } else {
                    userDTO.setSellerPendingDealsCount(0);
                }
                if (sellerDoneDealsCount.containsKey(user.getUserID())) {
                    userDTO.setSellerDoneDealsCount(sellerDoneDealsCount.get(user.getUserID()));
                } else {
                    userDTO.setSellerDoneDealsCount(0);
                }
                if (sellerRejectedDealsCount.containsKey(user.getUserID())) {
                    userDTO.setSellerRejectedDealsCount(sellerRejectedDealsCount.get(user.getUserID()));
                } else {
                    userDTO.setSellerRejectedDealsCount(0);
                }
                userDTOs.add(userDTO);
            }
            MessageDTO messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(userDTOs);
            return messageDTO;
        } catch (Exception e) {
            logger.error(BrokerxUserServiceBean.class + " updateBrokerDealsInItems() : ERROR " + e.toString());
        }
        return MessageDTO.getFailureDTO();
    }

    @Override
    public MessageDTO getAnalysisDropDownValues(Integer userID) {
        try {
            MessageDTO messageDTO;
            User user = em.find(User.class, userID);
            DropDownValuesDTO downValuesDTO = new DropDownValuesDTO();
            List<Integer> buyerIDs, sellerIDs;
            if (user.getIsBroker()) {
                Query buyerListQuery = em.createNativeQuery(QueryConstants.GET_BUYER_BY_BROKERID)
                        .setParameter("userID", userID);
                buyerIDs = buyerListQuery.getResultList();

                Query sellerListQuery = em.createNativeQuery(QueryConstants.GET_SELLERS_BY_BROKERID)
                        .setParameter("userID", userID);
                sellerIDs = sellerListQuery.getResultList();
            } else {
                Query itemQuery = em.createNativeQuery(QueryConstants.GET_DISTINCT_ITEMS_USER_DEALS_WITH)
                        .setParameter("userID", userID);
                List<String> items = itemQuery.getResultList();
                downValuesDTO.setItemsDealWith(items);

                Query buyerListQuery = em.createNativeQuery(QueryConstants.GET_BUYER_BY_USERID)
                        .setParameter("userID", userID);
                buyerIDs = buyerListQuery.getResultList();

                Query sellerListQuery = em.createNativeQuery(QueryConstants.GET_SELLERS_BY_USERID)
                        .setParameter("userID", userID);
                sellerIDs = sellerListQuery.getResultList();
            }
            if (buyerIDs != null && buyerIDs.size() > 0) {
                Query query = em.createQuery(QueryConstants.GET_USERS_BY_USER_IDS)
                        .setParameter("userIDs", buyerIDs);
                List<User> users = query.getResultList();
                downValuesDTO.setBuyers(users);
            }
            if (sellerIDs != null && sellerIDs.size() > 0) {
                Query query = em.createQuery(QueryConstants.GET_USERS_BY_USER_IDS)
                        .setParameter("userIDs", sellerIDs);
                List<User> users = query.getResultList();
                downValuesDTO.setSellers(users);
            }
            messageDTO = MessageDTO.getSuccessDTO();
            messageDTO.setData(downValuesDTO);
            return messageDTO;
        } catch (Exception e) {
            logger.error(BrokerxUserServiceBean.class + " getDropDownValues() : ERROR " + e.toString());
        }
        return MessageDTO.getFailureDTO();
    }
    
            

        }
