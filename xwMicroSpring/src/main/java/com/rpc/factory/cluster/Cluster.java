package com.rpc.factory.cluster;

import com.rpc.factory.RpcData;
import com.rpc.factory.balance.Balance;
import com.rpc.factory.protocol.Protocol;

public interface Cluster {

	Object cluster(Balance balance, Protocol protocol, RpcData rpcData) throws Exception;
}
