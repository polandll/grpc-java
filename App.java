public static void main(String[] args) {
  try (StargateClient stargateClient = configureStargateClient()) {
    // work with Stargate
  }
}
public static StargateClient configureStargateClient() {
  return StargateClient.builder()
    .withCqlContactPoints("localhost:9042")
    .withLocalDatacenter("datacenter1")
    .withAuthCredentials("cassandra", "cassandra")
    .withApiNode(new StargateNodeConfig("127.0.0.1"))
    .build();
}