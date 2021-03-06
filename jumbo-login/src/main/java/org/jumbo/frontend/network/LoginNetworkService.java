package org.jumbo.frontend.network;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.jumbo.commons.nio.services.NetworkAcceptor;

import java.nio.charset.Charset;

/**
 * Created by Return on 02/09/2014.
 */
public class LoginNetworkService extends NetworkAcceptor{
    @Inject
    Injector injector;

    @Override
    protected void configure() {
        acceptor.getFilterChain().addLast("login-codec-filter",
            new ProtocolCodecFilter(
            new TextLineCodecFactory(Charset.forName("UTF8"), LineDelimiter.NUL,
            new LineDelimiter("\n\0"))));

        IoHandler handler = new LoginNetworkHandler();
        injector.injectMembers(handler);

        acceptor.setHandler(handler);
    }
}
