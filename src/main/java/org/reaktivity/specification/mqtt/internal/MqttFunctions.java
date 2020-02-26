/**
 * Copyright 2016-2019 The Reaktivity Project
 *
 * The Reaktivity Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.reaktivity.specification.mqtt.internal;

import java.nio.charset.StandardCharsets;

import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.kaazing.k3po.lang.el.Function;
import org.kaazing.k3po.lang.el.spi.FunctionMapperSpi;
import org.reaktivity.specification.mqtt.internal.types.MqttPayloadFormat;
import org.reaktivity.specification.mqtt.internal.types.MqttPayloadFormatFW;
import org.reaktivity.specification.mqtt.internal.types.MqttRole;
import org.reaktivity.specification.mqtt.internal.types.control.MqttRouteExFW;
import org.reaktivity.specification.mqtt.internal.types.stream.MqttAbortExFW;
import org.reaktivity.specification.mqtt.internal.types.stream.MqttBeginExFW;
import org.reaktivity.specification.mqtt.internal.types.stream.MqttDataExFW;

public final class MqttFunctions
{
    @Function
    public static byte[] payloadFormat(String format)
    {
        final MqttPayloadFormat mqttPayloadFormat = MqttPayloadFormat.valueOf(format);
        final MqttPayloadFormatFW formatFW = new MqttPayloadFormatFW.Builder().wrap(new UnsafeBuffer(new byte[1]), 0, 1)
                                                                              .set(mqttPayloadFormat)
                                                                              .build();
        final byte[] array = new byte[formatFW.sizeof()];
        formatFW.buffer().getBytes(formatFW.offset(), array);

        return array;
    }

    @Function
    public static MqttRouteExBuilder routeEx()
    {
        return new MqttRouteExBuilder();
    }

    @Function
    public static MqttBeginExBuilder beginEx()
    {
        return new MqttBeginExBuilder();
    }

    @Function
    public static MqttDataExBuilder dataEx()
    {
        return new MqttDataExBuilder();
    }

    @Function
    public static MqttAbortExBuilder abortEx()
    {
        return new MqttAbortExBuilder();
    }

    public static final class MqttRouteExBuilder
    {
        private final MqttRouteExFW.Builder routeExRW;

        private MqttRouteExBuilder()
        {
            MutableDirectBuffer writeBuffer = new UnsafeBuffer(new byte[1024 * 8]);
            this.routeExRW = new MqttRouteExFW.Builder().wrap(writeBuffer, 0, writeBuffer.capacity());
        }

        public MqttRouteExBuilder topic(
            String topic)
        {
            routeExRW.topic(topic);
            return this;
        }

        public byte[] build()
        {
            final MqttRouteExFW routeEx = routeExRW.build();
            final byte[] array = new byte[routeEx.sizeof()];
            routeEx.buffer().getBytes(routeEx.offset(), array);
            return array;
        }
    }

    public static final class MqttBeginExBuilder
    {
        private final MqttBeginExFW.Builder beginExRW;

        private MqttBeginExBuilder()
        {
            MutableDirectBuffer writeBuffer = new UnsafeBuffer(new byte[1024 * 8]);
            this.beginExRW = new MqttBeginExFW.Builder().wrap(writeBuffer, 0, writeBuffer.capacity());
        }

        public MqttBeginExBuilder typeId(
            int typeId)
        {
            beginExRW.typeId(typeId);
            return this;
        }

        public MqttBeginExBuilder clientId(
            String clientId)
        {
            beginExRW.clientId(clientId);
            return this;
        }

        public MqttBeginExBuilder topic(
            String topic)
        {
            beginExRW.topic(topic);
            return this;
        }

        public MqttBeginExBuilder role(
            String role)
        {
            beginExRW.role(p -> p.set(MqttRole.valueOf(role)));
            return this;
        }

        public MqttBeginExBuilder subscriptionId(
            int id)
        {
            beginExRW.subscriptionId(id);
            return this;
        }

        public MqttBeginExBuilder userProperty(
            String name,
            String value)
        {
            if (value == null)
            {
                beginExRW.propertiesItem(p -> p.key(name)
                                               .value((String) null));
            }
            else
            {
                beginExRW.propertiesItem(p -> p.key(name)
                                               .value(value));
            }
            return this;
        }

        public byte[] build()
        {
            final MqttBeginExFW beginEx = beginExRW.build();
            final byte[] array = new byte[beginEx.sizeof()];
            beginEx.buffer().getBytes(beginEx.offset(), array);
            return array;
        }
    }

    public static final class MqttDataExBuilder
    {
        private final MqttDataExFW.Builder dataExRW;

        private MqttDataExBuilder()
        {
            MutableDirectBuffer writeBuffer = new UnsafeBuffer(new byte[1024 * 8]);
            this.dataExRW = new MqttDataExFW.Builder().wrap(writeBuffer, 0, writeBuffer.capacity());
        }

        public MqttDataExBuilder typeId(
            int typeId)
        {
            dataExRW.typeId(typeId);
            return this;
        }

        public MqttDataExBuilder topic(
            String topic)
        {
            dataExRW.topic(topic);
            return this;
        }

        public MqttDataExBuilder expiryInterval(
            int msgExp)
        {
            dataExRW.expiryInterval(msgExp);
            return this;
        }

        public MqttDataExBuilder contentType(
            String contentType)
        {
            dataExRW.contentType(contentType);
            return this;
        }

        public MqttDataExBuilder format(
            String format)
        {
            dataExRW.format(p -> p.set(MqttPayloadFormat.valueOf(format)));
            return this;
        }

        public MqttDataExBuilder responseTopic(
            String topic)
        {
            dataExRW.responseTopic(topic);
            return this;
        }

        public MqttDataExBuilder correlationInfo(
            String info)
        {
            dataExRW.correlationInfo(c -> c.bytes(b -> b.set(info.getBytes(StandardCharsets.UTF_8))));
            return this;
        }

        public MqttDataExBuilder userProperty(
            String name,
            String value)
        {
            if (value == null)
            {
                dataExRW.propertiesItem(p -> p.key(name)
                                              .value((String) null));
            }
            else
            {
                dataExRW.propertiesItem(p -> p.key(name)
                                              .value(value));
            }
            return this;
        }

        public byte[] build()
        {
            final MqttDataExFW dataEx = dataExRW.build();
            final byte[] array = new byte[dataEx.sizeof()];
            dataEx.buffer().getBytes(dataEx.offset(), array);
            return array;
        }
    }

    public static final class MqttAbortExBuilder
    {
        private final MqttAbortExFW.Builder abortExRW;

        private MqttAbortExBuilder()
        {
            MutableDirectBuffer writeBuffer = new UnsafeBuffer(new byte[1024 * 8]);
            this.abortExRW = new MqttAbortExFW.Builder().wrap(writeBuffer, 0, writeBuffer.capacity());
        }

        public MqttAbortExBuilder typeId(
            int typeId)
        {
            abortExRW.typeId(typeId);
            return this;
        }

        public MqttAbortExBuilder reason(
            int reason)
        {
            abortExRW.reason(reason);
            return this;
        }

        public byte[] build()
        {
            final MqttAbortExFW abortEx = abortExRW.build();
            final byte[] array = new byte[abortEx.sizeof()];
            abortEx.buffer().getBytes(abortEx.offset(), array);
            return array;
        }
    }

    public static class Mapper extends FunctionMapperSpi.Reflective
    {
        public Mapper()
        {
            super(MqttFunctions.class);
        }

        @Override
        public String getPrefixName()
        {
            return "mqtt";
        }
    }

    private MqttFunctions()
    {
        /* utility */
    }
}
