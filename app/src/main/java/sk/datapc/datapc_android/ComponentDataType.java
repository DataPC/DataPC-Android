package sk.datapc.datapc_android;

public class ComponentDataType {
    private int id;
    private String componentType;
    private String manufacurer;
    private String model;

    public ComponentDataType(int id, String ct, String ma, String m) {
        this.id = id;
        this.componentType = ct;
        this.manufacurer = ma;
        this.model = m;
    }

    public int getId() {
        return id;
    }

    public String getComponentType() {
        return componentType;
    }

    public String getManufacurer() {
        return manufacurer;
    }

    public String getModel() {
        return model;
    }
}
