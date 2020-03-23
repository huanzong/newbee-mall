package ltd.newbee.mall.service;


import ltd.newbee.mall.commententity.entity.Comment;
import ltd.newbee.mall.commententity.entity.Notification;
import ltd.newbee.mall.commententity.entity.User;
import ltd.newbee.mall.dto.NotificationDto;
import ltd.newbee.mall.dto.PageDto;
import ltd.newbee.mall.enums.notificationEnum;
import ltd.newbee.mall.dao.CommentMapper;
import ltd.newbee.mall.dao.NotificationMapper;
import ltd.newbee.mall.dao.QuestionMapper;
import ltd.newbee.mall.dao.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Resource
    private NotificationMapper notificationMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private CommentMapper commentMapper;

    //返回一个PageDto
    public PageDto list(int id, int page, int size) {
        PageDto pageDto = new PageDto();
        int totalcount = notificationMapper.count(id);
        pageDto.setPagination(totalcount, page, size);
        int offset = size * (page - 1);
        List<Notification> notifications = notificationMapper.list(id, offset, size);
        List<NotificationDto> notificationDtoList = new ArrayList<>();

        //将notification插入到notificationDto中，再将user信息也插入到notificationDto中
        //最后插入到notificationDtoList列表里
        for (Notification notification : notifications) {
            User user = userMapper.findById(notification.getNotifier());
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification, notificationDto);
            notificationDto.setNotifier(user);
            String outercontent;
            if (notification.getType() == notificationEnum.NOTIFICATION_QUESTION.getType()) {
                outercontent = questionMapper.gettitlebyid(notification.getOuterid());
                //插入问题的id
                notificationDto.setQuestionid(notification.getOuterid());
            } else {
                outercontent = commentMapper.getcontentbyid(notification.getOuterid());
                //插入问题的id
                Comment comment = commentMapper.getparentbyid(notification.getOuterid());
                notificationDto.setQuestionid(comment.getParent_id());
            }
            notificationDto.setOutercontent(outercontent);
            notificationDtoList.add(notificationDto);
        }
        //在pageDto中插入notificationDtoList
        pageDto.setData(notificationDtoList);
        return pageDto;
    }
}
