package org.graylog.plugins.cef.input;


import com.codahale.metrics.MetricRegistry;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.graylog.plugins.cef.codec.CEFCodec;
import org.graylog2.inputs.transports.UdpTransport;
import org.graylog2.plugin.LocalMetricRegistry;
import org.graylog2.plugin.ServerStatus;
import org.graylog2.plugin.configuration.Configuration;
import org.graylog2.plugin.inputs.MessageInput;
import org.graylog2.plugin.inputs.annotations.ConfigClass;
import org.graylog2.plugin.inputs.annotations.FactoryClass;

import javax.inject.Inject;

public class CEFUDPInput extends MessageInput {
    private static final String NAME = "CEF UDP Input";

    @AssistedInject
    public CEFUDPInput(@Assisted Configuration configuration,
                        MetricRegistry metricRegistry,
                        final UdpTransport.Factory udpTransportFactory,
                        final LocalMetricRegistry localRegistry,
                        CEFCodec.Factory codec,
                        Config config,
                        Descriptor descriptor,
                        ServerStatus serverStatus) {
        super(
                metricRegistry,
                configuration,
                udpTransportFactory.create(configuration),
                localRegistry,
                codec.create(configuration),
                config,
                descriptor,
                serverStatus
        );
    }

    @FactoryClass
    public interface Factory extends MessageInput.Factory<CEFUDPInput> {
        @Override
        CEFUDPInput create(Configuration configuration);

        @Override
        Config getConfig();

        @Override
        Descriptor getDescriptor();
    }

    public static class Descriptor extends MessageInput.Descriptor {
        @Inject
        public Descriptor() {
            super(NAME, false, "");
        }
    }

    @ConfigClass
    public static class Config extends MessageInput.Config {
        @Inject
        public Config(UdpTransport.Factory transport, CEFCodec.Factory codec) {
            super(transport.getConfig(), codec.getConfig());
        }
    }

}
