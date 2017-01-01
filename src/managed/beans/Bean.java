package managed.beans;

import javax.faces.bean.ManagedBean;

/**
 * Created by Quentin on 04/12/2016.
 */
@ManagedBean
public abstract class Bean {

    protected abstract boolean validate();
}
