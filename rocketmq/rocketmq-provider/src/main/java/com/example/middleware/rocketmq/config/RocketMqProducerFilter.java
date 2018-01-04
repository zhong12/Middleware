package com.example.middleware.rocketmq.config;

import com.zb.cloud.logcenter.http.filter.ZbHttpFilter;

import javax.servlet.annotation.WebFilter;

/**
 * Created by zhongjing on 2017/09/13.
 */
@WebFilter(filterName = "rocketMqProducerFilter", urlPatterns = "/*")
public class RocketMqProducerFilter extends ZbHttpFilter {
}
