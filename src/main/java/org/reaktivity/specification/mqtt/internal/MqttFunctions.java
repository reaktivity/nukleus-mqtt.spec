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

import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.kaazing.k3po.lang.el.Function;
import org.kaazing.k3po.lang.el.spi.FunctionMapperSpi;

import org.reaktivity.specification.mqtt.internal.types.stream.MqttBeginExFW;

public final class MqttFunctions
{
    @Function
    public static MqttBeginExBuilder beginEx()
    {
        return new MqttBeginExBuilder();
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
           String topic,
           int options)
        {
            beginExRW.topicsItem(a -> a.topic(topic).options(options));
            return this;
        }

        public MqttBeginExBuilder packetId(
           int packetId
        )
        {
            beginExRW.subscriptionDetails(b -> b.packetId(packetId));
            return this;
        }

        public MqttBeginExBuilder reason(
           int reason)
        {
            beginExRW.reasonCodesItem(b -> b.reasonCode(reason));
            return this;
        }

        public MqttBeginExBuilder packetType(
           int packetType)
        {
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
}
