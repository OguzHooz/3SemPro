package domain;

public class Batch {

    private float id;
    private float productType;
    private float amountToProduce;
    private int amountProduced;
    private float accepted;
    private float defective;

    public Batch() {

    }

    public Batch(float id, float productType, float amountToProduce, int amountProduced, float accepted, float defective) {
        this.id = id;
        this.productType = productType;
        this.amountToProduce = amountToProduce;
        this.amountProduced = amountProduced;
        this.accepted = accepted;
        this.defective = defective;
    }

    public float getId() {
        return id;
    }
    public void setId(float id) {
        this.id = id;
    }

    public float getProductType() {
        return productType;
    }

    public void setProductType(float productType) {
        this.productType = productType;
    }

    public float getAmountToProduce() {
        return amountToProduce;
    }

    public void setAmountToProduce(float amountToProduce) {
        this.amountToProduce = amountToProduce;
    }

    public float getAmountProduced() {
        return amountProduced;
    }

    public void setAmountProduced(int amountProduced) {
        this.amountProduced = amountProduced;
    }

    public float getAccepted() {
        return accepted;
    }

    public void setAccepted(float accepted) {
        this.accepted = accepted;
    }

    public float getDefective() {
        return defective;
    }

    public void setDefective(float defective) {
        this.defective = defective;
    }
}

