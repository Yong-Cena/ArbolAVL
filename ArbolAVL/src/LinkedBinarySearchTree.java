
import java.util.ArrayList;


public class LinkedBinarySearchTree <T extends Comparable <T>> {
    NodoBin<T> raiz;
    int cont;
    
    public LinkedBinarySearchTree()
    {
        raiz=null;
        cont=0;
    }
    
    public boolean isEmpty() {
        return raiz==null;
    }

    public int size() {
        return cont;
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
    
    public void inserta(T elem) {
        NodoBin<T> nuevo = new NodoBin<>(elem);
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
                if (elem.compareTo(actual.getElem()) < 0) 
                {
                    actual = actual.getIzq();
                } else {
                    actual = actual.getDer();
                }
            }

            if (elem.compareTo(papa.getElem()) < 0) 
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
    
    public String toStringNivel()
    {
        String respuesta;
        if(raiz!=null)
        {
            respuesta=" ARBOLITO"+"\n"+"-------------------"+"\n";
            respuesta+="["+raiz.getElem()+" ,"+raiz.getFe()+"]"+"\n";
        
            NodoBin<T> actual= raiz;
            ArrayQueue<NodoBin> cola= new ArrayQueue();
            ArrayList<T> lista= new ArrayList();
            cola.enqueue(raiz);
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
        
            int c=1,ex=1;
            for(int i=2;i<lista.size()-1;i=i+2)
            {
                respuesta+= "["+ lista.get(i)+" ,";
                respuesta+= lista.get(i+1)+"]"+" ";
                if(c== Math.pow(2, ex))
                {
                    respuesta+="\n";
                    ex++;
                    c=0;
                }
                c++;
            }
        }
        else
        {
            respuesta= "Arbol vacío";
        }
        
        return respuesta;
    }
    
    private NodoBin<T> balancear (NodoBin<T> alfa) {
        NodoBin<T> beta, gamma, b, c;
        if (alfa.getFe()== -2){ //izq
            beta = alfa.getIzq();
            if (alfa.getFe()== 1){ //izq-der
                gamma = beta.getDer();
                b = gamma.getIzq();
                c = gamma.getDer();
                if (alfa == raiz)
                {
                    gamma.setPapa(null);
                    raiz = gamma;
                }
                else
                {
                    alfa.getPapa().cuelga(gamma);
                }
                    beta.cuelga(b,'D');
                    alfa.cuelga(c,'I');
                    gamma.cuelga(beta,'I');
                    gamma.cuelga(alfa,'D');
                    
                    beta.setFe(0);
                    alfa.setFe(0);
                if (gamma.getFe() == 1)
                    beta.setFe(-1);
                else if (gamma.getFe() == -1)
                    alfa.setFe(1);
                gamma.setFe(0);
                return gamma;
            }
            else{//izq-izq
                gamma = beta.getIzq();
                //b = gamma.getDer();
                c = beta.getDer();
                if (alfa == raiz){
                    beta.setPapa(null);
                    raiz = beta;
                }
                else
                {
                    alfa.getPapa().cuelga(beta);
                }
                    
                    alfa.cuelga(c,'I');
                    beta.cuelga(gamma,'I');
                    beta.cuelga(alfa,'D');
                //actualizo factores de equilibro
                    if (beta.getFe() == -1)
                    {
                        alfa.setFe(0);
                        beta.setFe(0);
                    }
                    else
                    {
                        alfa.setFe(-1);
                        beta.setFe(1);
                    }
                return beta;
            }   
        }
        else{//der
            beta = alfa.getDer();
            if (beta.getFe() == -1){//der-izq
                gamma = beta.getIzq();
                b = gamma.getIzq();
                c = gamma.getDer();
                if (alfa == raiz)
                {
                    gamma.setPapa(null);
                    raiz = gamma;
                }
                else
                {
                   alfa.getPapa().cuelga(gamma);
                }
                    
                alfa.cuelga(b,'D');
                beta.cuelga(c,'I');
                gamma.cuelga(alfa,'I');
                gamma.cuelga(beta,'D');
                //actualizo factores de equilibro
                alfa.setFe(0);
                beta.setFe(0);
                if (gamma.getFe() == 1)
                    alfa.setFe(-1);
                else if (gamma.getFe() == -1)
                    beta.setFe(1);
                gamma.setFe(0);
                return gamma;
            }
            else{//der-der
                gamma = beta.getDer();
                b = beta.getIzq();
                //c = gamma.getIzq();
                if (alfa == raiz){
                    beta.setPapa(null);
                    raiz = beta;
                }
                else
                {
                    alfa.getPapa().cuelga(beta);
                }
                
                alfa.cuelga(b,'D');
                beta.cuelga(alfa,'I');
                beta.cuelga(gamma,'D');
                //actualizo factores de equilibro
                if (beta.getFe() == 1){
                    alfa.setFe(0);
                    beta.setFe(0);
                }
                else{
                    alfa.setFe(1);
                    beta.setFe(-1);
                }
                return beta;
            }
        }
    }
    
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
            
}
