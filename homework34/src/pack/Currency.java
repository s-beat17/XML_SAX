package pack;

public enum Currency {

    AZN(944),
    AUD(036),
    USD(840),
    EUR(978),
    GBP(826),
    BYN(933);

    private final int numCode;

    Currency(int numCode) {
        this.numCode = numCode;
    }

    public int numCode() {
        return numCode;
    }

    @Override
    public String toString() {
        return name();
    }
}
