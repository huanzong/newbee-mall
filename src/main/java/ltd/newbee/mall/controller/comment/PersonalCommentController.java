package ltd.newbee.mall.controller.comment;


import ltd.newbee.mall.commententity.entity.Question;
import ltd.newbee.mall.commententity.entity.User;
import ltd.newbee.mall.dto.NotificationDto;
import ltd.newbee.mall.dto.PageDto;
import ltd.newbee.mall.dao.NotificationMapper;
import ltd.newbee.mall.dao.UserMapper;
import ltd.newbee.mall.service.NotificationService;
import ltd.newbee.mall.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

//个人中心
@Controller
public class PersonalCommentController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionService questionService;
    @Resource
    private NotificationService notificationService;
    @Resource
    private NotificationMapper notificationMapper;
    @GetMapping("/personal1/{action}")
    public String personal(@PathVariable(name = "action")String action,
                           Model model,
                           HttpServletRequest request,
                           @RequestParam(name = "page",defaultValue = "1")int page,
                           @RequestParam(name = "size",defaultValue = "10")int size){
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "login";
        }
        User user = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String token = cookie.getValue();
                user = userMapper.findBytoken(token);
                if (user != null) {
                    request.getSession().setAttribute("user", user);
                    //获取未读的消息数量
                    int unreadnum=notificationMapper.getunreadcount(user.getId());
                    request.getSession().setAttribute("unreadnum",unreadnum);
                }
                break;
            }
        }
        if (action.equals("questions")){
            model.addAttribute("section","questions");
            model.addAttribute("sectionname","我的问题");
            PageDto<Question> pagination=questionService.list(user.getId(),page,size);
            model.addAttribute("pagination", pagination);
        }else if (action.equals("information")){
            model.addAttribute("section","information");
            model.addAttribute("sectionname","我的消息");
            PageDto<NotificationDto> notifications= notificationService.list(user.getId(),page,size);
            model.addAttribute("notifications",notifications);
        }


        return "personal";
    }
}
