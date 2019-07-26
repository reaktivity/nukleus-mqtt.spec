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
package org.reaktivity.specification.nukleus.mqtt.streams;

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

public class StreamIT
{
    private final K3poRule k3po = new K3poRule()
            .addScriptRoot("scripts", "org/reaktivity/specification/nukleus/mqtt/streams");

    private final TestRule timeout = new DisableOnDebug(new Timeout(5, SECONDS));

    @Rule
    public final TestRule chain = outerRule(k3po).around(timeout);

    @Test
    @Specification({
        "${scripts}/connect.as.receiver.then.close/client",
        "${scripts}/connect.as.receiver.then.close/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectAsReceiverThenClose() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @Specification({
            "${scripts}/connect.as.receiver.then.abort/client",
            "${scripts}/connect.as.receiver.then.abort/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectAsReceiverThenAbort() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect.as.receiver.with.exact.topic.filter/client",
        "${scripts}/connect.as.receiver.with.exact.topic.filter/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectAsReceiverWithExactTopicFilter() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @Specification({
            "${scripts}/connect.as.receiver.with.wildcard.topic.filter/client",
            "${scripts}/connect.as.receiver.with.wildcard.topic.filter/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectAsReceiverWithWildcardTopicFilter() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect.as.receiver.with.aggregated.topic.filters.both.exact/client",
        "${scripts}/connect.as.receiver.with.aggregated.topic.filters.both.exact/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectAsReceiverWithAggregatedTopicFiltersBothExact() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect.as.receiver.with.isolated.topic.filters.both.exact/client",
        "${scripts}/connect.as.receiver.with.isolated.topic.filters.both.exact/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectasReceiverWithIsolatedTopicFiltersBothExact() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect.as.receiver.with.aggregated.topic.filters.both.wildcard/client",
        "${scripts}/connect.as.receiver.with.aggregated.topic.filters.both.wildcard/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectAsReceiverWithAggregatedTopicFiltersBothWildcard() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect.as.receiver.with.isolated.topic.filters.both.wildcard/client",
        "${scripts}/connect.as.receiver.with.isolated.topic.filters.both.wildcard/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectAsReceiverWithIsolatedTopicFiltersBothWildcard() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect.as.receiver.with.aggregated.topic.filters.exact.and.wildcard/client",
        "${scripts}/connect.as.receiver.with.aggregated.topic.filters.exact.and.wildcard/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectAsReceiverWithAggregatedExactAndWildcardTopicFilters() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/connect.as.receiver.with.isolated.topic.filters.exact.and.wildcard/client",
        "${scripts}/connect.as.receiver.with.isolated.topic.filters.exact.and.wildcard/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldConnectAsReceiverWithIsolatedExactAndWildcardTopicFilters() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/send.to.client.at.most.once/client",
        "${scripts}/send.to.client.at.most.once/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldSendToClientAtMostOnce() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/send.to.server.at.most.once/client",
        "${scripts}/send.to.server.at.most.once/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldSendToServerAtMostOnce() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/send.to.server.at.most.once.then.disconnect/client",
        "${scripts}/send.to.server.at.most.once.then.disconnect/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/mqtt#0\"")
    public void shouldSendToServerAtMostOnceThenDisconnect() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }
}
