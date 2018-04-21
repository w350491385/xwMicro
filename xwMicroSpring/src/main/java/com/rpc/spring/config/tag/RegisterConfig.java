package com.rpc.spring.config.tag;

import com.rpc.spring.config.register.impl.EtcdRegisterServerImpl;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class RegisterConfig {

	private String id;//id
	private String registerType;//注册服务器类型etcd
	private String address;//多个注册地址用;隔开
	private String webApp;//本地应用项目
	private String localPort;//本地端口
	private String group;//分组名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebApp() {
		return webApp;
	}

	public void setWebApp(String webApp) {
		this.webApp = webApp;
	}

	public String getLocalPort() {
		return localPort;
	}

	public void setLocalPort(String localPort) {
		this.localPort = localPort;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getRootDir(){
		return this.getGroup() + "/" + this.getWebApp();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RegisterConfig)) return false;

		RegisterConfig that = (RegisterConfig) o;

		if (!getId().equals(that.getId())) return false;
		if (!getRegisterType().equals(that.getRegisterType())) return false;
		if (!getAddress().equals(that.getAddress())) return false;
		if (!getWebApp().equals(that.getWebApp())) return false;
		if (!getLocalPort().equals(that.getLocalPort())) return false;
		return getGroup().equals(that.getGroup());

	}

	@Override
	public int hashCode() {
		int result = getId().hashCode();
		result = 31 * result + getRegisterType().hashCode();
		result = 31 * result + getAddress().hashCode();
		result = 31 * result + getWebApp().hashCode();
		result = 31 * result + getLocalPort().hashCode();
		result = 31 * result + getGroup().hashCode();
		return result;
	}
}
