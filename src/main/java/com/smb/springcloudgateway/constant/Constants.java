package com.smb.springcloudgateway.constant;

public interface Constants {
    String tokenSecretKey = "Babl@1234";
    long sessionTimeoutMinute = 10;
    //RSE private and public key
    String RSE_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEogIBAAKCAQEAkgY7GHZ59yZyqkDAmBnhpMeYZIrQJs04ifdQZZOwA2i8+atu\n" +
            "5vro5TYKlO4o7DPco49dlAYvw4mMW9ry1nv1jjmQKDn0YlxD3GxANh9scn5eOMQb\n" +
            "K6n/u/t5r+ODLUXV63x43W7dO+hN3n0dVZrkhBn8CsS4EzCz8rxJVUhg1UFfXkq0\n" +
            "XHficKJDo7Yh9Sw7y0cyn320OOqZyY8vkB3TOWiWe+qNPtQZ/8xB5WB99gRECfxl\n" +
            "TQ3jvXzq68X4TVyEB8CB6SI7bfHr0PiuqDn2XkymWp8Y2w9OFzDXQ+Ly8/YuaE5m\n" +
            "BOKHzzmTdjSZCgiA1OUkBE9yGneiI4lBaMHFFwIDAQABAoIBAD0UWCG6ZPfpYCsw\n" +
            "3J6CrOy1BFyB5+FjFBQ5m6pxZT130RwKBmCdcU93FGQJXpq2csMDk9v33oDHByvg\n" +
            "4JSw0xzDnTYGT9/YwhXwPcely0H088qA1PClsi/aV6Mn4lXMkVdoAFJ3LhkB6Zh3\n" +
            "8w48tPtAtzl14Z09UPfmpDIFDWQFyme04WP/8wthe/rWyxt2F3WmyJZsCKFqqGUf\n" +
            "CxQMsJ7vd5ZUgSWokxWs9CvTLtdmoCfVPZqvMfaQ2NGPTbBlN3UVsft80/33uY2t\n" +
            "Tt3a14qTmfq+vglnPHmI4zHvAh1kYdsDby9ACD+OJq3ePvX8b8RCNcC3llL/r8SI\n" +
            "LKmba4ECgYEA2KOxmPLtt30RhlF5hlWRy9XviinS9hJTmKpnny65tYlSIXtkg5jz\n" +
            "dNZm3pwa9mfxkfsSf5J9abMv3RFjSYkj+8l8yveBnDCNyFvlEQTZiUVeiT+tdx3o\n" +
            "fO3Kj3OYWTjYtlFPxBaZwTRjf5pCas0OFFc28Y90fxgRafoNGGHE+1cCgYEArI4Y\n" +
            "yLN617jxHGKxi/qWdbafMvfl0+yahHi+zSMAd2q8+oviF+8+k1dcRlao7tzjAdyo\n" +
            "pO298MO4q0CAl42vH/l76fWIYkBHierF0eLmmZeR7UT/k4+8PICLVrhC3h+TVGc4\n" +
            "XLTZgrnkj0J8JFGCmiG2u4Ux2Ek7lNPI7m3tLEECgYAVPQ8XOJG3kGpcabZpRhBS\n" +
            "FHo0aF9HQoqxh9s6zuDj/D9yCt/SyvbrOG3DWziFebFEJ4IrwZfL+gYyPRcnyWse\n" +
            "ryUt3M/3GQd9Ygz1+yy8eXGIMRb3e5YkUUevgpyn+pu8XAJe7fxK5bDvfJcQU2QS\n" +
            "+G3phq/cGebzJdGM12DxZwKBgCg/RHybkE18r5BFXNAfdLSkEeHbfFm/2BKJ70b9\n" +
            "oQA5fzaZJfUbMmc/eyBU2jZMgw5OH2/InALqQ8p90wCGZZB5qnNZd6QjBgyfG+Dm\n" +
            "oY61ka3b6BjM78TU3qstcztH9h87rCPDcvBQ14SqpzX5ZRL6Rhra6FNgUWtXMoia\n" +
            "TzWBAoGAF9Xa859eAWWdIKm+xJF5Vd9aP2Qf7NsbJ2L/pbW4aEv1gsTP9yjKlP9I\n" +
            "SY84JbNh8kw8gzdkP6+/VFOunUAwjH+qfVXVWWznQIZgLHYJRkONmn9+lGEfdmNK\n" +
            "rmJQlmiRIbItSXahNCVckQr5u7lo+UCTEXwsrf/etMiqOacJLeo=\n" +
            "-----END RSA PRIVATE KEY-----";
    String RSE_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkgY7GHZ59yZyqkDAmBnh\n" +
            "pMeYZIrQJs04ifdQZZOwA2i8+atu5vro5TYKlO4o7DPco49dlAYvw4mMW9ry1nv1\n" +
            "jjmQKDn0YlxD3GxANh9scn5eOMQbK6n/u/t5r+ODLUXV63x43W7dO+hN3n0dVZrk\n" +
            "hBn8CsS4EzCz8rxJVUhg1UFfXkq0XHficKJDo7Yh9Sw7y0cyn320OOqZyY8vkB3T\n" +
            "OWiWe+qNPtQZ/8xB5WB99gRECfxlTQ3jvXzq68X4TVyEB8CB6SI7bfHr0PiuqDn2\n" +
            "XkymWp8Y2w9OFzDXQ+Ly8/YuaE5mBOKHzzmTdjSZCgiA1OUkBE9yGneiI4lBaMHF\n" +
            "FwIDAQAB\n" +
            "-----END PUBLIC KEY-----";

}
