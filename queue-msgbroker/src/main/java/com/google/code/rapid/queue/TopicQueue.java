package com.google.code.rapid.queue;

import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.google.code.rapid.queue.util.RouterKeyUtil;

/**
 * 主题消息，用于客户端消费数据使用
 * 
 * 	Durable (the queue will survive a broker restart)
 *	Exclusive (used by only one connection and the queue will be deleted when that connection closes)
 *	Auto-delete (queue is deleted when last consumer unsubscribes)
 * @author badqiu
 *
 */
public class TopicQueue {
	
	private List<String> routerKeyList;
	private BlockingQueue<byte[]> queue;
	
	private String queueName;
	private String remarks; // 备注
	
	private boolean durable;
	private boolean autoDelete; //auto delete queue by timeout
	private int maxSize;
	
	public List<String> getRouterKeyList() {
		return routerKeyList;
	}

	public void setRouterKeyList(List<String> routerKeyList) {
		this.routerKeyList = routerKeyList;
	}

	public boolean matchRouterKey(String routerKeyValue) {
		return RouterKeyUtil.matchRouterKey(routerKeyList, routerKeyValue);
	}

	public BlockingQueue<byte[]> getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue<byte[]> queue) {
		this.queue = queue;
	}
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isDurable() {
		return durable;
	}

	public void setDurable(boolean durable) {
		this.durable = durable;
	}

	public boolean isAutoDelete() {
		return autoDelete;
	}

	public void setAutoDelete(boolean autoDelete) {
		this.autoDelete = autoDelete;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public void truncate() {
		throw new UnsupportedOperationException(); //FIXME truncate()
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((queueName == null) ? 0 : queueName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TopicQueue other = (TopicQueue) obj;
		if (queueName == null) {
			if (other.queueName != null)
				return false;
		} else if (!queueName.equals(other.queueName))
			return false;
		return true;
	}
	
}
