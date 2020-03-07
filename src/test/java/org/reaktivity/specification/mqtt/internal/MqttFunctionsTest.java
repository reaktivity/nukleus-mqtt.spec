/**
 * Copyright 2016-2020 The Reaktivity Project
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

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.kaazing.k3po.lang.internal.el.ExpressionFactoryUtils.newExpressionFactory;

import java.util.Objects;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

import org.agrona.DirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.junit.Before;
import org.junit.Test;
import org.kaazing.k3po.lang.internal.el.ExpressionContext;
import org.reaktivity.specification.mqtt.internal.types.control.MqttRouteExFW;
import org.reaktivity.specification.mqtt.internal.types.stream.MqttAbortExFW;
import org.reaktivity.specification.mqtt.internal.types.stream.MqttBeginExFW;
import org.reaktivity.specification.mqtt.internal.types.stream.MqttDataExFW;

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
    public void shouldEncodePayloadFormat()
    {
        final byte[] bytes = MqttFunctions.payloadFormat("TEXT");
        final byte[] expected = new byte[]{1};
        assertArrayEquals(bytes, expected);
    }

    @Test
    public void shouldEncodeMqttRouteEx()
    {
        final byte[] array = MqttFunctions.routeEx()
                .topic("sensor/one")
                .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttRouteExFW mqttRouteEx = new MqttRouteExFW().wrap(buffer, 0, buffer.capacity());
        assertEquals(mqttRouteEx.topic().asString(), "sensor/one");
    }

    @Test
    public void shouldEncodeMqttBeginExtAsSubscribe()
    {
        final byte[] array = MqttFunctions.beginEx()
                .typeId(0)
                .role("RECEIVER")
                .clientId("client")
                .topic("sensor/one")
                .subscriptionId(1)
                .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttBeginExFW mqttBeginEx = new MqttBeginExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals("RECEIVER", mqttBeginEx.role().toString());
        assertEquals("client", mqttBeginEx.clientId().asString());
        assertEquals("sensor/one", mqttBeginEx.topic().asString());
        assertEquals(1, mqttBeginEx.subscriptionId());
    }

    @Test
    public void shouldEncodeMqttBeginExAsSuback()
    {
        final byte[] array = MqttFunctions.beginEx()
                .typeId(0)
                .role("SENDER")
                .clientId("client")
                .topic("sensor/one")
                .subscriptionId(1)
                .userProperty("name", "value")
                .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttBeginExFW mqttBeginEx = new MqttBeginExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals("SENDER", mqttBeginEx.role().toString());
        assertEquals("client", mqttBeginEx.clientId().asString());
        assertEquals("sensor/one", mqttBeginEx.topic().asString());
        assertEquals(1, mqttBeginEx.subscriptionId());
        assertNotNull(mqttBeginEx.properties()
                                .matchFirst(h ->
                                                "name".equals(h.key().asString()) &&
                                                    "value".equals(h.value().asString())) != null);
    }

    @Test
    public void shouldEncodeMqttBeginExAsSubackWithNullUserPropertyValue()
    {
        final byte[] array = MqttFunctions.beginEx()
                .typeId(0)
                .role("SENDER")
                .clientId("client")
                .topic("sensor/one")
                .subscriptionId(1)
                .userProperty("name", null)
                .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttBeginExFW mqttBeginEx = new MqttBeginExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals("SENDER", mqttBeginEx.role().toString());
        assertEquals("client", mqttBeginEx.clientId().asString());
        assertEquals("sensor/one", mqttBeginEx.topic().asString());
        assertEquals(1, mqttBeginEx.subscriptionId());
        assertNotNull(mqttBeginEx.properties()
                                 .matchFirst(h ->
                                                 "name".equals(h.key().asString()) &&
                                                     Objects.isNull(h.value())) != null);
    }

    @Test
    public void shouldEncodeMqttDataEx()
    {
        final byte[] array = MqttFunctions.dataEx()
                .typeId(0)
                .topic("sensor/one")
                .expiryInterval(15)
                .contentType("message")
                .format("TEXT")
                .responseTopic("sensor/one")
                .correlation("request-id-1")
                .userProperty("name", "value")
                .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttDataExFW mqttDataEx = new MqttDataExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals(0, mqttDataEx.typeId());
        assertEquals("sensor/one", mqttDataEx.topic().asString());
        assertEquals(15, mqttDataEx.expiryInterval());
        assertEquals("message", mqttDataEx.contentType().asString());
        assertEquals("TEXT", mqttDataEx.format().toString());
        assertEquals("sensor/one",  mqttDataEx.responseTopic().asString());
        assertEquals("MQTT_BINARY [length=12, bytes=octets[12]]",  mqttDataEx.correlation().toString());
        assertNotNull(mqttDataEx.properties()
                                .matchFirst(h ->
                                                "name".equals(h.key().asString()) &&
                                                    "value".equals(h.value().asString())) != null);
    }

    @Test
    public void shouldEncodeMqttDataExWithBytes()
    {
        final byte[] array = MqttFunctions.dataEx()
                                          .typeId(0)
                                          .topic("sensor/one")
                                          .expiryInterval(15)
                                          .contentType("message")
                                          .format("TEXT")
                                          .responseTopic("sensor/one")
                                          .correlationBytes("request-id-1".getBytes(UTF_8))
                                          .userProperty("name", "value")
                                          .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttDataExFW mqttDataEx = new MqttDataExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals(0, mqttDataEx.typeId());
        assertEquals("sensor/one", mqttDataEx.topic().asString());
        assertEquals(15, mqttDataEx.expiryInterval());
        assertEquals("message", mqttDataEx.contentType().asString());
        assertEquals("TEXT", mqttDataEx.format().toString());
        assertEquals("sensor/one",  mqttDataEx.responseTopic().asString());
        assertEquals("MQTT_BINARY [length=12, bytes=octets[12]]",  mqttDataEx.correlation().toString());
        assertNotNull(mqttDataEx.properties()
                                .matchFirst(h ->
                                                "name".equals(h.key().asString()) &&
                                                    "value".equals(h.value().asString())) != null);
    }

    @Test
    public void shouldEncodeMqttDataExWithNullUserPropertyValue()
    {
        final byte[] array = MqttFunctions.dataEx()
                                          .typeId(0)
                                          .topic("sensor/one")
                                          .expiryInterval(15)
                                          .contentType("message")
                                          .format("TEXT")
                                          .responseTopic("sensor/one")
                                          .correlation("request-id-1")
                                          .userProperty("name", null)
                                          .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttDataExFW mqttDataEx = new MqttDataExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals(0, mqttDataEx.typeId());
        assertEquals("sensor/one", mqttDataEx.topic().asString());
        assertEquals(15, mqttDataEx.expiryInterval());
        assertEquals("message", mqttDataEx.contentType().asString());
        assertEquals("TEXT", mqttDataEx.format().toString());
        assertEquals("sensor/one",  mqttDataEx.responseTopic().asString());
        assertEquals("MQTT_BINARY [length=12, bytes=octets[12]]",  mqttDataEx.correlation().toString());
        assertNotNull(mqttDataEx.properties()
                                .matchFirst(h ->
                                                "name".equals(h.key().asString()) &&
                                                    Objects.isNull(h.value())) != null);
    }

    @Test
    public void shouldEncodeMqttAbortExAsUnsubscribe()
    {
        final byte[] array = MqttFunctions.abortEx()
                .typeId(0)
                .reason(0xf9)
                .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        MqttAbortExFW mqttAbortEx = new MqttAbortExFW().wrap(buffer, 0, buffer.capacity());
        assertEquals(0, mqttAbortEx.typeId());
        assertEquals(0xf9, mqttAbortEx.reason());
    }
}
