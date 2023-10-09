
public class NodoBin <T extends Comparable <T>>{
    private T elem;
    private NodoBin<T> izq, der, papa;
    private int fe;
    
    public NodoBin()
    {
        
    }
    
    public NodoBin(T elem)
    {
        this.elem= elem;
    }
    
    public int numDescendientes()
    {
        int des=0;  
        if(izq!=null)
        {
            des= izq.numDescendientes()+1;
        }
        if(der!=null)
        {
            des+= der.numDescendientes()+1;
        }
        return des;
    }
    
    public T getElem()
    {
        return elem;
    }
    
    public int getFe()
    {
        return fe;
    }

    public NodoBin<T> getIzq() {
        return izq;
    }

    public NodoBin<T> getDer() {
        return der;
    }

    public NodoBin<T> getPapa() {
        return papa;
    }
    
    public void setIzq(NodoBin<T> izq) {
        this.izq = izq;
    }

    public void setDer(NodoBin<T> der) {
        this.der = der;
    }

    public void setPapa(NodoBin<T> papa) {
        this.papa = papa;
    }
    
    public void setFe(int fe)
    {
        this.fe=fe;
    }
    
    public void cuelga(NodoBin <T> papa)
    {
        if(elem.compareTo(papa.getElem())<0)
        {
            papa.setIzq(this);
        }
        else
        {
            papa.setDer(this);
        }
        this.papa = papa;
    }
    
    public String toString()
    {
        String respuesta="";
        
        respuesta+="Elem: "+elem.toString()+" Factor de equilibrio: "+fe;
        return respuesta;
    }
    
}
