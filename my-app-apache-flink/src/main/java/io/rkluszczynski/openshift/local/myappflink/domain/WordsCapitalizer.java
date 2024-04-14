package io.rkluszczynski.openshift.local.myappflink.domain;

import java.util.Locale;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

public class WordsCapitalizer implements MapFunction<String, String> {

    @Override
    public String map(String s) throws Exception {
        return s.toLowerCase(Locale.ROOT);
    }
}
