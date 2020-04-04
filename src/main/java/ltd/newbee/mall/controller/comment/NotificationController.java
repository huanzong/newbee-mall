package ltd.newbee.mall.controller.comment;


import ltd.newbee.mall.dao.CommentMapper;
import ltd.newbee.mall.dao.NotificationMapper;
import ltd.newbee.mall.enums.notificationEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

//将通知设置为已读，并且跳转到问题页面
@Controller
public class NotificationController {

    @Resource
    private NotificationMapper notificationMapper;
    @Resource
    private CommentMapper commentMapper;

    /**
     * 获取评论或回复的回复信息
     *
     * @param id  回复数据的id
     * @param request
     * @return
     */
    @GetMapping("/notification/{action}")
    public String notification(@PathVariable("action") int id,
                               HttpServletRequest request) {
        //将通知设置为已读
        notificationMapper.updatestatus(id);
        //获取type，检验是回复评论还是回复问题，1是回复了评论 2是回复了问题
        int type = notificationMapper.gettypebyid(id);
        //获取所回复的评论或者问题的数据id
        int outerid = notificationMapper.getouteridbyid(id);
        int questionid;
        //判断如果是对问题的回复
        if (type == notificationEnum.NOTIFICATION_QUESTION.getType()) {
            //查询所回复的问题的详情信息
            questionid = outerid;
        } else {
            //判断如果是对评论的回复，获取所评论的评论数据的id
            int commentid = commentMapper.getparentidbyid(id);
            questionid = commentMapper.getparentidbyid(commentid);
        }
        return "redirect:/question/" + questionid;
    }
}
