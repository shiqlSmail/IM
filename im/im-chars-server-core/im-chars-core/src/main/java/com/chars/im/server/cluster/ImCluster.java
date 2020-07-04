package com.chars.im.server.cluster;

/**
 *
 * @desc IM集群抽象类
 */
public abstract class ImCluster implements ICluster {
	/**
	 * IM集群配置
	 */
	protected ImClusterConfig clusterConfig;

	public ImCluster(ImClusterConfig clusterConfig){
		this.clusterConfig = clusterConfig;
	}
	public ImClusterConfig getClusterConfig() {
		return clusterConfig;
	}
}
