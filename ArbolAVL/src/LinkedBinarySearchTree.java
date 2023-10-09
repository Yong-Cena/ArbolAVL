
import java.util.ArrayList;


public class LinkedBinarySearchTree <T extends Comparable <T>> {
    NodoBin<T> raiz;
    int cont;
    
    public LinkedBinarySearchTree()
    {
        raiz=null;
        cont=0;
    }
    
    public void inserta(T elem) {
        NodoBin<T> nuevo = new NodoBin<T>(elem);
        if (raiz == null) 
        {
            raiz = nuevo;
        } 
        else 
        {
            NodoBin<T> papa = null;
            NodoBin<T> actual = raiz;

            while (actual != null) 
            {
                papa = actual;
                if (elem.compareTo(actual.getElem()) <= 0) 
                {
                    actual = actual.getIzq();
                } else {
                    actual = actual.getDer();
                }
            }

            if (elem.compareTo(papa.getElem()) <= 0) 
            {
                papa.setIzq(nuevo);
            } else {
                papa.setDer(nuevo);
            }
            nuevo.cuelga(papa);
            actualizarFe(nuevo);
        }
        cont++;
    }
    
    private void actualizarFe(NodoBin<T> actual)
    {
        while(actual!=null)
        {
            int fe= calculaFe(actual);
            actual.setFe(fe);
            actual= actual.getPapa();
        }
    }
    
    public int calculaFe(NodoBin<T> actual)
    {
        int resp;
        int izq, der;
        izq= getAltura(actual.getIzq());
        der= getAltura(actual.getDer());
        resp= der-izq;
        return resp;
    }
    
/*    private NodoBin<T> insertaR(NodoBin<T> actual, NodoBin<T> nuevo) 
    {
        if (actual == null) 
        {
            return nuevo;
        }

        if (nuevo.getElem().compareTo(actual.getElem()) <= 0) {
            actual.setIzq(insertaR(actual.getIzq(), nuevo));
        } else {
            actual.setDer(insertaR(actual.getDer(), nuevo));
        }
        
        return actual;
    }
*/    
    public void borra (T elem)
    {
        NodoBin<T> actual=encuentra(elem);
        NodoBin<T> papa;
        cont--;
        
        if(actual==null)
        {
            return;
        }
        
        papa= actual.getPapa();
        if(actual.getIzq()==null && actual.getDer()==null)
        {
            if(actual==raiz)
            {
                raiz=null;
            }
            else
            {
                if(papa.getElem().compareTo(actual.getElem())<=0)
                {
                    papa.setDer(null);
                }
                else
                {
                    papa.setIzq(null);
                    papa.setDer(null);
                }
            }
        }
        else if(actual.getIzq()==null || actual.getDer()==null)
        {
            NodoBin<T> hijo;
            if(actual.getIzq()==null)
            {
                hijo=actual.getDer();
            }
            else
            {
                hijo=actual.getIzq();
            }
            hijo.cuelga(papa);
            if(raiz.equals(actual))
            {
                raiz=hijo;
                hijo.setPapa(actual);
            }
        }
        else
        {
            NodoBin<T> suc=actual.getDer();
            while(suc.getIzq()!=null)
            {
                suc=suc.getIzq();
            }
            papa=suc.getPapa();
            
            if(suc.getDer()==null)
            {
                //
            }
            else
            {
                papa.cuelga(suc.getDer());
            }
        }
        actualizarFe(papa);
    }

    public Object findMin() {
        NodoBin<T> actual=raiz;
        while(actual!=null && actual.getIzq()!=null)
        {
            actual= actual.getIzq();
        }
        return actual;
    }

    public Object findMax() {
        NodoBin<T> actual=raiz;
        while(actual!=null && actual.getDer()!=null)
        {
            actual= actual.getDer();
        }
        return actual;
    }

    public boolean isEmpty() {
        return raiz==null;
    }

    public int size() {
        return cont;
    }
    
    public NodoBin<T> encuentra(T elem) {
        return encuentraR(elem,raiz);
    }

    public NodoBin<T> encuentraR(T elem, NodoBin<T> actual) {
        if(actual==null)
        {
            return null;
        }
        
        if(actual.getElem().equals(elem))
        {
            return actual;
        }
        NodoBin<T> res= encuentraR(elem, actual.getIzq());
        if(res==null)
        {
            res=encuentraR(elem, actual.getDer());
        }
        return res;
    }
    
    
    /*
    public void insertaAVL(Nodo <T> nuevo)
    {
        Nodo <T> actual=nuevo;
        while(!termine && actual!= raiz)
            papa=actual.papa;
            if(actual.elem<papa.elem)
                papa.fe+=1;
            else
                papa.fe+=1;
            if(Math.abs(papa.fe)==2)
                termine=true;
                actual=rota(papa);
            else
                termine= (papa.fe==0);
                actual=actual.papa;
    }
    */
    
    public void checarFe(NodoBin<T> actual)
    {
        NodoBin<T> papa= actual.getPapa();
        int feP= papa.getFe();
        boolean termine=false;
        
        while(!termine && actual!=raiz)
        {
            papa=actual.getPapa();
            if(actual==papa.getIzq())
            {
                papa.setFe(feP+1);
            }
            else
            {
                papa.setFe(feP-1);
            }
            
            if(Math.abs(feP)==2)
            {
//                actual=rotar(actual);
            }
            else
            {
                if(Math.abs(feP)==1)
                {
                    termine=true;
                }
            }
            actual=papa;
        }
        
    }
    
    public int getAltura(NodoBin<T> actual)
    {
        if(actual==null)
        {
            return 0;
        }
        int a1= getAltura(actual.getIzq());
        int a2= getAltura(actual.getDer());
        return Math.max(a1,a2)+1;
    }
    
    public String toStringNivel()
    {
        String respuesta="";
        
        NodoBin<T> actual= raiz;
        ArrayQueue<NodoBin> cola= new ArrayQueue();
        ArrayList<T> lista= new ArrayList();
        cola.enqueue(raiz);
        actual=raiz;
        while(actual!=null && !cola.isEmpty())
        {
            actual= cola.dequeue();
            lista.add(actual.getElem());
            Integer fe= actual.getFe();
            lista.add((T) fe);
             if(actual.getIzq()!=null)
            {
                cola.enqueue(actual.getIzq());
            }
            if(actual.getDer()!=null)
            {
                cola.enqueue(actual.getDer());
            }
        }
        
        respuesta+=lista.get(0)+","+ lista.get(1)+"\n";
        for(int i=2;i<lista.size()-1;i=i+2)
        {
            respuesta+= "["+ lista.get(i)+" ,";
            respuesta+= lista.get(i+1)+"]"+" ";
            if(i%4==0)
            {
                respuesta+="\n";
            }
        }

        return respuesta;
    }
            
}
