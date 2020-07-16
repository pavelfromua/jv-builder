/**
 * <p>Дано класс Plane. Написать класс PlaneBuilder, который реализует паттерн Builder.
 * Добавьте не менее пяти полей в класс Plain. Внести все необходимые изминения в класс Plane
 * которые могут потребоваться для реализации паттерна Builder.</p>
 */

public class Plane {
    private String type;
    private String model;
    private double weight;
    private boolean isAirfreight;
    private boolean hasAutopilot;

    public Plane(PlaneBuilder builder) {
        this.type = builder.type;
        this.hasAutopilot = builder.hasAutopilot;
        this.isAirfreight = builder.isAirfreight;
        this.model = builder.model;
        this.weight = builder.weight;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isAirfreight() {
        return isAirfreight;
    }

    public boolean isHasAutopilot() {
        return hasAutopilot;
    }

    public class PlaneBuilder {
        private String type;
        private String model;
        private double weight;
        private boolean isAirfreight;
        private boolean hasAutopilot;

        public PlaneBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public PlaneBuilder setModel(String model) {
            this.model = model;
            return this;
        }

        public PlaneBuilder setWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public PlaneBuilder setIsAirfreight(boolean isAirfreight) {
            this.isAirfreight = isAirfreight;
            return this;
        }

        public PlaneBuilder setHasAutopilot(boolean hasAutopilot) {
            this.hasAutopilot = hasAutopilot;
            return this;
        }

        public Plane build() {
            Plane newPlane = new Plane(this);

            return newPlane;
        }
    }
}
