/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.muriloao.itss.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 *
 * @author Murilo Oliveira
 */
@Component
@Primary
@EnableAutoConfiguration
public class DocumentationController implements SwaggerResourcesProvider {

    /**
     * Swagger configuratios
     *
     * @return
     */
    @Override
    public List get() {
        List resources = new ArrayList<>();
        resources.add(swaggerResource("car-service", "/api/car/v2/api-docs", "2.0"));
        resources.add(swaggerResource("customer-service", "/api/customer/v2/api-docs", "2.0"));
        resources.add(swaggerResource("parking-service", "/api/parking/v2/api-docs", "2.0"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}
