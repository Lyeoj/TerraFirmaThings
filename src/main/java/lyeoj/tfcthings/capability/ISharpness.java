package lyeoj.tfcthings.capability;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

public interface ISharpness extends ICapabilityProvider {

    public int getCharges();

    public void setCharges(int charges);

    public void addCharge();

    public void removeCharge();
}
