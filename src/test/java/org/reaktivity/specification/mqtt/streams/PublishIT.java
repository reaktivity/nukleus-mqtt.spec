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
package org.reaktivity.specification.mqtt.streams;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.rules.RuleChain.outerRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.kaazing.k3po.junit.annotation.ScriptProperty;
import org.kaazing.k3po.junit.annotation.Specification;
import org.kaazing.k3po.junit.rules.K3poRule;

public class PublishIT
{
    private final K3poRule k3po = new K3poRule()
            .addScriptRoot("scripts", "org/reaktivity/specification/mqtt");
    private final TestRule timeout = new DisableOnDebug(new Timeout(5, SECONDS));

    @Rule
    public final TestRule chain = outerRule(k3po).around(timeout);

    @Test
    @Specification({
        "${scripts}/publish.one.message/client",
        "${scripts}/publish.one.message/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldPublishOneMessage() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/publish.multiple.messages/client",
        "${scripts}/publish.multiple.messages/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldPublishMultipleMessages() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/publish.multiple.messages.with.delay/client",
        "${scripts}/publish.multiple.messages.with.delay/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldPublishMultipleMessagesWithDelay() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.notifyBarrier("PUBLISH_CLOSE");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/subscribe.one.message/client",
        "${scripts}/subscribe.one.message/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldSubscribeOneMessage() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/subscribe.one.message.with.pattern.topic/client",
        "${scripts}/subscribe.one.message.with.pattern.topic/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldSubscribeOneMessageWithPatternTopic() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/publish.message.and.subscribe.correlated.message/client",
        "${scripts}/publish.message.and.subscribe.correlated.message/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldPublishMessageAndSubscribeCorrelatedMessage() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/subscribe.one.message.with.invalid.subscription.id/client",
        "${scripts}/subscribe.one.message.with.invalid.subscription.id/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldSubscribeOneMessageWithInvalidSubscriptionId() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/subscribe.one.message.with.packetId/client",
        "${scripts}/subscribe.one.message.with.packetId/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectSubscribeOneMessageWithPacketId() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }
}
