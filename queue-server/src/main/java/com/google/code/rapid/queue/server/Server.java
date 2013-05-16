package com.google.code.rapid.queue.server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.google.code.rapid.queue.server.thrift.MessageBrokerService;
import com.google.code.rapid.queue.server.thrift.MessageBrokerService.Processor;
import com.google.code.rapid.queue.server.util.SpringContext;

public class Server {
	public static int DEFAULT_PORT = 29088;
	private int port = DEFAULT_PORT;
	
	public void startServer() {
		try {
			MessageBrokerService.Iface iface = SpringContext.getBean(MessageBrokerService.Iface.class);
			TServerTransport serverTransport = new TServerSocket(port);

			MessageBrokerService.Processor processor = new Processor(iface);

			Factory portFactory = new TBinaryProtocol.Factory(true, true);

			Args args = new Args(serverTransport);
			args.maxWorkerThreads(2000);
			args.minWorkerThreads(8);
			args.processor(processor);
			args.protocolFactory(portFactory);
			TServer server = new TThreadPoolServer(args); // 有多种server可选择
			server.setServerEventHandler(new TServerEventHandlerImpl());
			System.out.println("start server on port:"+port);
			server.serve();
		} catch (TTransportException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.startServer();
	}
}