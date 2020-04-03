package org.apache.jmeter.sampler.gui;

import java.util.HashMap;
import java.util.Map;

import com.webank.weevent.sdk.BrokerException;
import com.webank.weevent.sdk.IWeEventClient;
import com.webank.weevent.sdk.SendResult;
import com.webank.weevent.sdk.SendResult.SendResultStatus;
import com.webank.weevent.sdk.WeEvent;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

public class MyWeEventProduct extends AbstractJavaSamplerClient {
    private String topic = "com.webank.weevent.jmeter";
    private String groupId = "1";
    private int size = 1;
    private String format = "json";
    private String defaultUrl = "\"http://127.0.0.1:8080/weevent\"";
    private Map<String, String> extensions = new HashMap();
    private IWeEventClient weEventClient;
    private WeEvent weEvent;

    public MyWeEventProduct(){};

    public void setupTest(JavaSamplerContext context) {
        this.getNewLogger().info("this is producer setupTest");
        super.setupTest(context);
        this.defaultUrl = context.getParameter("url")==null
            ? this.defaultUrl:context.getParameter("url");
        try {
            weEventClient = IWeEventClient.build(defaultUrl);
        } catch (BrokerException e) {
            e.printStackTrace();
        }
        this.topic = context.getParameter("topic") == null
            ? this.topic : context.getParameter("topic");
        this.groupId = context.getParameter("groupId") == null
            ? this.groupId : context.getParameter("groupId");
        this.format = context.getParameter("format") == null
            ? this.format : context.getParameter("format");
        this.size = context.getIntParameter("size") <= 0
            ? this.size : context.getIntParameter("size");
        StringBuffer buffer = new StringBuffer();

        for(int i = 0; i < this.size; ++i) {
            buffer.append("a");
        }

        this.weEvent = new WeEvent(this.topic, buffer.toString().getBytes(), this.extensions);
        this.getNewLogger().info("weEvent:{}", this.weEvent);
        boolean result = false;
        try {
            result = this.weEventClient.open(this.topic);
        } catch (BrokerException e) {
            e.printStackTrace();
        }
        this.getNewLogger().info("open topic result: {}", result);

    }

    public void teardownTest(JavaSamplerContext arg0) {
        this.getNewLogger().debug(this.getClass().getName() + ": teardownTest");
    }

    public Arguments getDefaultParameters() {
        Arguments arguments = new Arguments();
        arguments.addArgument("topic", this.topic);
        arguments.addArgument("size", String.valueOf(this.size));
        arguments.addArgument("format", this.format);
        arguments.addArgument("url", this.defaultUrl);
        return arguments;
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult result = new SampleResult();
        result.setSampleLabel("product");
        result.sampleStart();
        try {
            SendResult sendResult = this.weEventClient.publish(this.weEvent, this.groupId);
            result.setSuccessful(sendResult.getStatus() == SendResultStatus.SUCCESS && sendResult.getEventId().length() > 0);
            result.setResponseMessage(sendResult.getEventId());
        } catch (BrokerException e) {
            e.printStackTrace();
            this.getNewLogger().error("publish exception", e);
            result.sampleEnd();
            result.setSuccessful(false);
            result.setResponseMessage(e.getMessage());
        }
        result.sampleEnd();
        return result;
    }
}
