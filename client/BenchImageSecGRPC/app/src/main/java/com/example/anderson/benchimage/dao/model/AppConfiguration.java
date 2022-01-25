package com.example.anderson.benchimage.dao.model;

public final class AppConfiguration {
    private String local;
    private String image;
    private String filter;
    private String size;
    private String ip_cloudlet;
    private String outputDirectory;
    private String rpc_method;

    private boolean BenchmarkMode = false;

    public AppConfiguration() {
        this(null, null, null, null, null, null);
    }

    public AppConfiguration(String local, String image, String filter,
                            String size, String ip_cloudlet, String rpc_method) {
        this.local = local;
        this.image = image;
        this.filter = filter;
        this.size = size;
        this.ip_cloudlet = ip_cloudlet;
        this.rpc_method = rpc_method;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public String getOutputDirectory() { return outputDirectory; }

    public void setIpCloudlet(String ip_cloudlet) { this.ip_cloudlet = ip_cloudlet; }

    public String getIpCloudlet() {
        return ip_cloudlet;
    }

    public boolean getBenchmarkMode() { return BenchmarkMode; }

    public void setBenchmarkMode(boolean benchmark) { BenchmarkMode = benchmark; }

    public String getRpcMethod() { return rpc_method; }

    public void setRpcMethod(String method) { rpc_method = method; }

    @Override
    public String toString() {
        return "AppConfiguration [local=" + local + ", image=" + image + ", filter=" + filter
                + ", size=" + size + ", outputDirectory=" + outputDirectory + ", ip_cloudlet=" + ip_cloudlet + "]";
    }
}