package guru.springframework.msscbreweryclient.web.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BlockingRestTemplateCustomiser implements RestTemplateCustomizer {

  private int totalConnections;
  private int defaultMaxConnectionsPerRoute;
  private int requestTimeout;
  private int socketTimeout;

  public BlockingRestTemplateCustomiser(
      @Value("${config.connection.totalConnections}") int totalConnections,
      @Value("${config.connection.defaultMaxConnectionsPerRoute}") int defaultMaxConnectionsPerRoute,
      @Value("${config.connection.requestTimeout}") int requestTimeout,
      @Value("${config.connection.socketTimeout}") int socketTimeout)
  {
    this.totalConnections = totalConnections;
    this.defaultMaxConnectionsPerRoute = defaultMaxConnectionsPerRoute;
    this.requestTimeout = requestTimeout;
    this.socketTimeout = socketTimeout;
  }

  private ClientHttpRequestFactory clientHttpRequestFactory() {
    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(totalConnections);
    connectionManager.setDefaultMaxPerRoute( defaultMaxConnectionsPerRoute);

    RequestConfig requestConfig = RequestConfig
        .custom()
        .setConnectionRequestTimeout( requestTimeout )
        .setSocketTimeout( socketTimeout )
        .build();

    CloseableHttpClient httpClient = HttpClients
        .custom()
        .setConnectionManager( connectionManager )
        .setKeepAliveStrategy( new DefaultConnectionKeepAliveStrategy())
        .setDefaultRequestConfig( requestConfig )
        .build();

    return new HttpComponentsClientHttpRequestFactory( httpClient );
  }

  @Override
  public void customize(RestTemplate restTemplate) {
    restTemplate.setRequestFactory( this.clientHttpRequestFactory());
  }

  public void setTotalConnections(int totalConnections) {
    this.totalConnections = totalConnections;
  }

  public void setDefaultMaxConnectionsPerRoute(int defaultMaxConnectionsPerRoute) {
    this.defaultMaxConnectionsPerRoute = defaultMaxConnectionsPerRoute;
  }

  public void setRequestTimeout(int requestTimeout) {
    this.requestTimeout = requestTimeout;
  }

  public void setSocketTimeout(int socketTimeout) {
    this.socketTimeout = socketTimeout;
  }
}
