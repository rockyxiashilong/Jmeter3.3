package org.apache.jmeter.assertions;

import com.google.gson.Gson;

import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.ThreadListener;

import java.io.Serializable;
import java.util.logging.Logger;


public class JSONAssertion extends AbstractTestElement implements Serializable,Assertion, ThreadListener {

    private static final Logger log = Logger.getLogger("jemeter");
    private static final long serialVersionUID = 240L;

    @Override
    public AssertionResult getResult(SampleResult response) {
        AssertionResult result = new AssertionResult(getName());
        String resultData = response.getResponseDataAsString();
        if (resultData.length()==0){
            return result.setResultForNull();
        }
        else{
            Gson gson = new Gson();
            if (!gson.toJsonTree(resultData).isJsonObject()){
                log.info("Cannot parse result content");
                result.setFailure(true);
                result.setFailureMessage("ResultData is not Json");
            }
        }
        return result;
    }

    @Override
    public void threadStarted() {

    }

    @Override
    public void threadFinished() {

    }
}
