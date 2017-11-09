package com.blysin.leshop.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Blysin
 * @since 2017/11/2
 */
@Controller
@RequestMapping("socket")
public class WebSocketController {

    @RequestMapping("/chat")
    public String toChatRoom() {
        return "websocket/chat";
    }
}
