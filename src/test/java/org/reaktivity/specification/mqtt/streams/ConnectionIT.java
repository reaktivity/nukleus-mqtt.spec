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

public class ConnectionIT
{
    private final K3poRule k3po = new K3poRule()
            .addScriptRoot("scripts", "org/reaktivity/specification/mqtt");
    private final TestRule timeout = new DisableOnDebug(new Timeout(5, SECONDS));

    @Rule
    public final TestRule chain = outerRule(k3po).around(timeout);

    @Test
    @Specification({
        "${scripts}/connect/successful/client",
        "${scripts}/connect/successful/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnect() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/server.assigned.client.id/client",
        "${scripts}/connect/server.assigned.client.id/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectWithServerAssignedClientId() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/reject.missing.client.id/client",
        "${scripts}/connect/reject.missing.client.id/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectMissingClientId() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/disconnect/client",
        "${scripts}/disconnect/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectThenDisconnect() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/invalid.protocol.version/client",
        "${scripts}/connect/invalid.protocol.version/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectInvalidProtocolVersion() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/invalid.flags/client",
        "${scripts}/connect/invalid.flags/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectMalformedConnectPacket() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/disconnect/invalid.fixed.header.flags/client",
        "${scripts}/disconnect/invalid.fixed.header.flags/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectMalformedDisconnectPacket() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/reject.second.connect/client",
        "${scripts}/connect/reject.second.connect/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectSecondConnectPacket() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/reject.connect.when.topic.alias.maximum.repeated/client",
        "${scripts}/reject.connect.when.topic.alias.maximum.repeated/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectConnectWhenTopicAliasMaximumRepeated() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/successful.fragmented/client",
        "${scripts}/connect/successful.fragmented/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldProcessFragmentedConnectPacket() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/client.sent.close/client",
        "${scripts}/client.sent.close/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldReceiveClientSentClose() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/client.sent.abort/client",
        "${scripts}/client.sent.abort/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldReceiveClientSentAbort() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/client.sent.reset/client",
        "${scripts}/client.sent.reset/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldReceiveClientSentReset() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/disconnect.after.keep.alive.timeout/client",
        "${scripts}/disconnect.after.keep.alive.timeout/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldDisconnectClientAfterKeepAliveTimeout() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/keep.alive.with.pingreq/client",
        "${scripts}/keep.alive.with.pingreq/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldKeepAliveWithPingreq() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/timeout.before.connect/client",
        "${scripts}/timeout.before.connect/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldTimeoutBeforeConnect() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/maximum.qos.0/client",
        "${scripts}/connect/maximum.qos.0/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectWithMaximumQos0() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/retain.unavailable/client",
        "${scripts}/connect/retain.unavailable/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectWithRetainUnavailable() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/wildcard.subscriptions.unavailable/client",
        "${scripts}/connect/wildcard.subscriptions.unavailable/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectWithWildcardSubscriptionsUnavailable() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/subscription.identifiers.unavailable/client",
        "${scripts}/connect/subscription.identifiers.unavailable/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectWithSubscriptionIdentifiersUnavailable() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/shared.subscriptions.unavailable/client",
        "${scripts}/connect/shared.subscriptions.unavailable/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectWithSharedSubscriptionsUnavailable() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/reject.username/client",
        "${scripts}/connect/reject.username/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectConnectWithUsername() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/reject.password/client",
        "${scripts}/connect/reject.password/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectConnectWithPassword() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/reject.will.flag/client",
        "${scripts}/connect/reject.will.flag/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectWillFlag() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/reject.invalid.will.qos/client",
        "${scripts}/connect/reject.invalid.will.qos/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectInvalidWillQos() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/reject.will.qos.1.without.will.flag/client",
        "${scripts}/connect/reject.will.qos.1.without.will.flag/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectWillQos1WithoutWillFlag() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/reject.will.qos.2.without.will.flag/client",
        "${scripts}/connect/reject.will.qos.2.without.will.flag/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectWillQos2WithoutWillFlag() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/reject.will.retain.without.will.flag/client",
        "${scripts}/connect/reject.will.retain.without.will.flag/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldRejectWillRetainWithoutWillFlag() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/will.message.with.abrupt.disconnect/client",
        "${scripts}/connect/will.message.with.abrupt.disconnect/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectWithWillMessageThenAbruptDisconnect() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect/will.message.with.normal.disconnect/client",
        "${scripts}/connect/will.message.with.normal.disconnect/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectWithWillMessageThenNormalDisconnect() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect.with.will.message.then.publish.one.message/client",
        "${scripts}/connect.with.will.message.then.publish.one.message/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectWithWillMessageThenPublishOneMessage() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect.with.clean.start.0.and.will.message/client",
        "${scripts}/connect.with.clean.start.0.and.will.message/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectWithCleanStart0AndWillMessage() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect.with.clean.start.1.and.will.message/client",
        "${scripts}/connect.with.clean.start.1.and.will.message/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectWithCleanStart1AndWillMessage() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }
}
