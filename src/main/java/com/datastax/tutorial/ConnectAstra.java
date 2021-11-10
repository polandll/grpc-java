package com.datastax.tutorial;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.stargate.grpc.StargateBearerToken;
import io.stargate.proto.QueryOuterClass;
import io.stargate.proto.QueryOuterClass.Row;
import io.stargate.proto.StargateGrpc;

public class ConnectAstra {
    
    private static final String ASTRA_DB_ID      = "<id>";
    private static final String ASTRA_DB_REGION  = "<region>";
    private static final String ASTRA_TOKEN      = "<token>";
    private static final String ASTRA_KEYSPACE   = "<keyspace>";
    
    public static void main(String[] args) 
    throws Exception {
        
        
        //-------------------------------------
        // 1. Initializing Connectivity 
        //-------------------------------------
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(ASTRA_DB_ID + "-" + ASTRA_DB_REGION + ".apps.astra.datastax.com", 443)
                .useTransportSecurity()
                .build();
        
        // blocking stub version
        StargateGrpc.StargateBlockingStub blockingStub =
            StargateGrpc.newBlockingStub(channel)
                .withDeadlineAfter(10, TimeUnit.SECONDS)
                .withCallCredentials(new StargateBearerToken(ASTRA_TOKEN));
        
        //-------------------------------------
        // 2. Create Schema
        //-------------------------------------
       
        blockingStub.executeQuery(
                QueryOuterClass.Query.newBuilder()
                    .setCql("CREATE TABLE IF NOT EXISTS " + ASTRA_KEYSPACE + ".users (firstname text PRIMARY KEY, lastname text);")
                    .build());
        System.out.println("Table 'users' has been created.");
        
        //-------------------------------------
        // 3. Insert 2 rows with Batch
        //-------------------------------------
        
        blockingStub.executeBatch(
                QueryOuterClass.Batch.newBuilder()
                    .addQueries(
                        QueryOuterClass.BatchQuery.newBuilder()
                            .setCql("INSERT INTO " + ASTRA_KEYSPACE + ".users (firstname, lastname) VALUES('Jane', 'Doe')")
                            .build())
                    .addQueries(
                        QueryOuterClass.BatchQuery.newBuilder()
                            .setCql("INSERT INTO " + ASTRA_KEYSPACE + ".users (firstname, lastname) VALUES('Serge', 'Provencio')")
                            .build())
                    .build());
        System.out.println("2 rows have been inserted in table users.");
        
        //-------------------------------------
        // 4. Retrieving result.
        //-------------------------------------
        
        QueryOuterClass.Response queryString = blockingStub.executeQuery(QueryOuterClass
                .Query.newBuilder()
                .setCql("SELECT firstname, lastname FROM " + ASTRA_KEYSPACE + ".users")
                .build());
        QueryOuterClass.ResultSet rs = queryString.getResultSet();
        for (Row row : rs.getRowsList()) {
            System.out.println(""
                    + "firstname=" + row.getValues(0).getString() + ", "
                    + "lastname=" + row.getValues(1).getString());
        }
        
        System.out.println("Everything worked!");
    }

}
