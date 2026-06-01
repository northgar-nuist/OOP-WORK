package com.escape.item;

public abstract class Item {
    protected final String name;
    protected final String description;

    protected Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    public abstract String useEffect();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item other)) return false;
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() { return name.toLowerCase().hashCode(); }
}
