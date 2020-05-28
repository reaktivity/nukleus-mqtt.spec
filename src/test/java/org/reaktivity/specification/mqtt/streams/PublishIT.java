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
        k3po.awaitBarrier("PUBLISHED_MESSAGE_TWO");
        k3po.notifyBarrier("PUBLISH_MESSAGE_THREE");
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
        "${scripts}/subscribe.one.message.then.publish.message/client",
        "${scripts}/subscribe.one.message.then.publish.message/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldSubscriberOneMessageThenPublishMessage() throws Exception
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
        "${scripts}/reject.publish.one.message.with.packet.id/client",
        "${scripts}/reject.publish.one.message.with.packet.id/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectPublishOneMessageWithPacketId() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/reject.publish.when.topic.alias.exceeds.maximum/client",
        "${scripts}/reject.publish.when.topic.alias.exceeds.maximum/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectPublishWhenTopicAliasExceedsThanMaximum() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/reject.publish.when.topic.alias.repeated/client",
        "${scripts}/reject.publish.when.topic.alias.repeated/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectPublishWhenTopicAliasRepeated() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/publish.rejected/client",
        "${scripts}/publish.rejected/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectPublish() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/topic.not.routed/client",
        "${scripts}/topic.not.routed/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectTopicNotRouted() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/publish.with.user.property/client",
        "${scripts}/publish.with.user.property/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldPublishWithUserProperty() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/publish.with.user.properties.repeated/client",
        "${scripts}/publish.with.user.properties.repeated/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldPublishWithRepeatedUserProperties() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/publish.with.user.properties.distinct/client",
        "${scripts}/publish.with.user.properties.distinct/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldPublishWithDistinctUserProperties() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/subscribe.fails.then.publish.message/client",
        "${scripts}/subscribe.fails.then.publish.message/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldFailSubscribeThenPublishMessage() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/publish.message.with.topic.alias/client",
        "${scripts}/publish.message.with.topic.alias/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldPublishMessageWithTopicAlias() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/publish.messages.with.topic.alias.distinct/client",
        "${scripts}/publish.messages.with.topic.alias.distinct/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldPublishMessagesWithTopicAliasDistinct() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/publish.messages.with.topic.alias.repeated/client",
        "${scripts}/publish.messages.with.topic.alias.repeated/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldPublishMessagesWithTopicAliasRepeated() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/publish.messages.with.topic.alias.replaced/client",
        "${scripts}/publish.messages.with.topic.alias.replaced/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldPublishMessagesWithTopicAliasReplaced() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }
}
