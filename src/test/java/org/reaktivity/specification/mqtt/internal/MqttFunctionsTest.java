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

import static org.junit.Assert.assertEquals;
import static org.kaazing.k3po.lang.internal.el.ExpressionFactoryUtils.newExpressionFactory;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import org.agrona.DirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.junit.Before;
import org.junit.Test;
import org.kaazing.k3po.lang.internal.el.ExpressionContext;

import org.reaktivity.specification.mqtt.internal.types.control.MqttRouteExFW;
import org.reaktivity.specification.mqtt.internal.types.stream.MqttBeginExFW;
import org.reaktivity.specification.mqtt.internal.types.stream.MqttDataExFW;
import org.reaktivity.specification.mqtt.internal.types.stream.MqttEndExFW;

public class MqttFunctionsTest
{
    private ExpressionFactory factory;
    private ELContext ctx;

    @Before
    public void setUp() throws Exception
    {
        factory = newExpressionFactory();
        ctx = new ExpressionContext();
    }

    @Test
    public void shouldGetMapper()
    {
        MqttFunctions.Mapper mapper = new MqttFunctions.Mapper();
        assertEquals("mqtt", mapper.getPrefixName());
    }

    @Test
    public void shouldEncodeMqttRouteEx()
    {
        final byte[] array = MqttFunctions.routeEx()
                .topic("a/b")
                .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttRouteExFW mqttRouteEx = new MqttRouteExFW().wrap(buffer, 0, buffer.capacity());
        assertEquals(mqttRouteEx.topic().asString(), "a/b");
    }

    @Test
    public void shouldEncodeMqttBeginExtAsSubscribe()
    {
        final byte[] array = MqttFunctions.beginEx()
                .typeId(0)
                .role("RECEIVER")
                .clientId("abcd")
                .packetId(1)
                .topic("a/b", 0)
                .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttBeginExFW mqttBeginEx = new MqttBeginExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals("RECEIVER", mqttBeginEx.role().toString());
        assertEquals("abcd", mqttBeginEx.clientId().asString());
        assertEquals(1, mqttBeginEx.packetId());
        mqttBeginEx.topics().forEach(topic ->
        {
            assertEquals("a/b", topic.topic().asString());
            assertEquals(0, topic.options());
        });
    }

    @Test
    public void shouldEncodeMqttBeginExAsSuback()
    {
        final byte[] array = MqttFunctions.beginEx()
                .typeId(0)
                .role("SENDER")
                .clientId("abcd")
                .packetId(1)
                .reason(0x00)
                .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttBeginExFW mqttBeginEx = new MqttBeginExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals("SENDER", mqttBeginEx.role().toString());
        assertEquals("abcd", mqttBeginEx.clientId().asString());
        assertEquals(1, mqttBeginEx.packetId());
        mqttBeginEx.reasonCodes().forEach(reason ->
        {
            assertEquals(0x00, reason.reasonCode());
        });
    }

    @Test
    public void shouldEncodeMqttDataEx()
    {
        final byte[] array = MqttFunctions.dataEx()
                .typeId(0)
                .packetId(1)
                .topic("a/b")
                .messageExpiry(15)
                .contentType("message")
                .payloadFormat("TEXT")
                .respTopicInfo("a/b", "info")
                .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttDataExFW mqttDataEx = new MqttDataExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals(0, mqttDataEx.typeId());
        assertEquals(1, mqttDataEx.packetId());
        assertEquals("a/b", mqttDataEx.topic().asString());
        assertEquals(15, mqttDataEx.messageExpiry());
        assertEquals("message", mqttDataEx.contentType().asString());
        assertEquals("TEXT", mqttDataEx.payloadFormat().toString());
        assertEquals("a/b",  mqttDataEx.respTopicInfo().topic().asString());
        assertEquals("MQTT_BINARY [length=4, bytes=octets[4]]",  mqttDataEx.respTopicInfo().correlationInfo().toString());
    }

    @Test
    public void shouldEncodeMqttEndExAsUnsubscribe()
    {
        final byte[] array = MqttFunctions.endEx()
                .typeId(0)
                .topic("a/b")
                .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttEndExFW mqttEndEx = new MqttEndExFW().wrap(buffer, 0, buffer.capacity());

        mqttEndEx.topics().forEach(topic ->
        {
            assertEquals("a/b", topic.value().asString());
        });
    }

    @Test
    public void shouldEncodeMqttEndExAsUnsubscribeWithUserProperty()
    {
        final byte[] array = MqttFunctions.endEx()
                .typeId(0)
                .topic("a/b")
                .userProperty("key", "value")
                .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttEndExFW mqttEndEx = new MqttEndExFW().wrap(buffer, 0, buffer.capacity());

        mqttEndEx.topics().forEach(topic ->
        {
            assertEquals("a/b", topic.value().asString());
        });

        mqttEndEx.userProperties().forEach(p ->
        {
            assertEquals("key", p.key().asString());
            assertEquals("value", p.value().asString());
        });
    }

    @Test
    public void shouldEncodeMqttEndExAsUnsuback()
    {
        final byte[] array = MqttFunctions.endEx()
                .typeId(0)
                .reason(0x00)
                .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttEndExFW mqttEndex = new MqttEndExFW().wrap(buffer, 0, buffer.capacity());

        mqttEndex.reasonCodes().forEach(reason ->
        {
            assertEquals(0x00, reason.reasonCode());
        });
    }
}
