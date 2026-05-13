package com.zzyl;

import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import com.huaweicloud.sdk.iotda.v5.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class IoTDeviceTest {

    @Autowired
    private IoTDAClient client;

    @Test
    public void listAllProject(){
        ListProductsRequest request = new ListProductsRequest();
        ListProductsResponse response = client.listProducts(request);
        List<ProductSummary> products = response.getProducts();
        for (ProductSummary product : products) {
            System.out.println(product.getProductId() + "," + product.getName());
        }
        //System.out.println(response.toString());
    }

    @Test
    public void testGetDeviceInfo(){
        ShowDeviceRequest request = new ShowDeviceRequest();
        request.withDeviceId("683195f70ed8b074bf7d6f4a_hw_watcher_01");
        try {
            ShowDeviceResponse response = client.showDevice(request);

            System.out.println(response.toString());
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (RequestTimeoutException e) {
            e.printStackTrace();
        } catch (ServiceResponseException e) {
            e.printStackTrace();
            System.out.println(e.getHttpStatusCode());
            System.out.println(e.getRequestId());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMsg());
        }
    }

    @Test
    public void testGetDeviceShadowData(){
        ShowDeviceShadowRequest request = new ShowDeviceShadowRequest();
        request.withDeviceId("683195f70ed8b074bf7d6f4a_hw_watcher_01");
        try {
            ShowDeviceShadowResponse response = client.showDeviceShadow(request);
            //System.out.println(response.toString());
            List<DeviceShadowData> list = response.getShadow();
            for (DeviceShadowData deviceShadowData : list) {
                DeviceShadowProperties reported = deviceShadowData.getReported();
                System.out.println("reported.getProperties() = " + reported.getProperties());
                System.out.println("reported.getEventTime() = " + reported.getEventTime());
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (RequestTimeoutException e) {
            e.printStackTrace();
        } catch (ServiceResponseException e) {
            e.printStackTrace();
            System.out.println(e.getHttpStatusCode());
            System.out.println(e.getRequestId());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMsg());
        }
    }
}
