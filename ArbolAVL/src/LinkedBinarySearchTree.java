
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
    
    private void actualizarFe(NodoBin actual)
    {
        if(actual!=null)
        {
            int fe= calculaFe(actual);
            actual.setFe(fe);
            actualizarFe(actual.getDer());
            actualizarFe(actual.getIzq());
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
    
    public NodoBin<T> encuentraFe2(NodoBin<T> actual)
    {
        if(actual==null)
        {
            return null;
        }
        
        if(Math.abs(actual.getFe())==2)
        {
            return actual;
        }
        NodoBin izq= encuentraFe2(actual.getDer());
        NodoBin der= encuentraFe2(actual.getIzq());
        
        if(izq!=null)
        {
            return izq;
        }
        return der;
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
            } else 
            {
                papa.setDer(nuevo);
            }
            nuevo.cuelga(papa);
            actualizarFe(raiz);
            while(nuevo!=null)
            {
                if(Math.abs(nuevo.getFe())==2)
                {
                    balancear(nuevo);
                }
                nuevo=nuevo.getPapa();
            }
        }
        cont++;
    }
       
    public void borra (T elem)
    {
        NodoBin<T> actual=encuentra(elem); 
        System.out.println("actual: "+actual);
        if(actual==null)
        {
            return;
        }
        
        cont--;
        NodoBin papa= actual.getPapa();
        if(actual.getIzq()==null && actual.getDer()==null) //hoja
        {
            if(actual==raiz)
            {
                raiz=null;
            }
            else
            {
                if(papa.getIzq()==actual)
                {
                    papa.setIzq(null);
                }
                else
                {
                    papa.setDer(null);
                }
            }
        }
        else if(actual.getIzq()!=null && actual.getDer()!=null) //con hijos
        {
            NodoBin<T> suin= actual.getDer();
            while(suin.getIzq()!=null)
            {
                suin=suin.getIzq();
            }
            actual.setElem(suin.getElem());
            if(suin.getDer()!=null)
            {
                suin.getDer().cuelga(suin.getPapa(),'I');
            }
            else
            {
                suin.getPapa().setDer(null);
            }
        }
        else //rama
        {
            NodoBin<T> hijo= actual.getDer();
            if(hijo==null)
            {
                hijo= actual.getDer();
            }
            if(actual==raiz)
            {
                raiz=hijo;
                raiz.setPapa(null);
            }
            else
            {
                hijo.cuelga(papa);
            }
        }
        actualizarFe(raiz);
        NodoBin aux= encuentraFe2(raiz);
        if(aux!=null)
        {
            balancear(aux);
        }
    }
    
    public NodoBin<T> encuentra(T elem) {
        return encuentraR(elem,raiz);
    }

    private NodoBin<T> encuentraR(T elem, NodoBin<T> actual) {
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
                System.out.println(fe);
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
*/
    
    String[] niveles;
    public void imprimirNivel() {
        niveles = new String[this.getAltura(raiz)];

        imprimirNivel(raiz, 0);
        for (int i = 0; i < niveles.length; i++) {
            System.out.println(niveles[i]);
        }
    }

    private void imprimirNivel(NodoBin pivote, int nivel2) {
        if (pivote != null) {
            niveles[nivel2] = "[ "+pivote.getElem() + ", FE: " + pivote.getFe()+"] "+((niveles[nivel2] != null) ? niveles[nivel2] : "");
            imprimirNivel(pivote.getDer(), nivel2 + 1);
            imprimirNivel(pivote.getIzq(), nivel2 + 1);
        }
    }
    
    private NodoBin<T> balancear (NodoBin<T> alfa) {
        NodoBin<T> beta, gamma, b, c;
        if (alfa.getFe()== -2)
        { //izq
            beta = alfa.getIzq();
            if (alfa.getFe()== 1)
            { //izq-der
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
                    gamma.cuelga(alfa.getPapa());
                }
                  
                if(b!=null)
                {
                    b.cuelga(beta,'D');
                }
                else
                {
                    beta.setDer(null);
                }
                
                if(c!=null)
                {
                  c.cuelga(alfa,'I');  
                }
                else
                {
                    alfa.setIzq(null);
                }
                alfa.cuelga(gamma,'D');
                beta.cuelga(gamma,'I');
                actualizarFe(raiz);
                return gamma;
            }
            else
            {//izq-izq
                gamma = beta.getIzq();
                c = beta.getDer();
                if (alfa == raiz){
                    beta.setPapa(null);
                    raiz = beta;
                }
                else
                {
                    beta.cuelga(alfa.getPapa());
                }
                
                if(c!=null)
                {
                    c.cuelga(alfa,'I');
                }
                else
                {
                    alfa.setIzq(null);
                }
                gamma.cuelga(beta,'I');
                alfa.cuelga(beta,'D');
                actualizarFe(raiz);
                return beta;
            }   
        }
        else
        {//der
            beta = alfa.getDer();
            if (beta.getFe() == -1)
            {//der-izq
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
                   gamma.cuelga(alfa.getPapa());
                }
                
                if(b!=null)
                {
                    b.cuelga(alfa,'D');
                }
                else
                {
                    alfa.setDer(null);
                }
                if(c!=null)
                {
                    c.cuelga(beta,'I');
                }
                else
                {
                    beta.setIzq(null);
                }
                alfa.cuelga(gamma,'I');
                beta.cuelga(gamma,'D');
                actualizarFe(raiz);
                return gamma;
            }
            else
            {
                gamma = beta.getDer();
                b = beta.getIzq();
                if (alfa == raiz){
                    beta.setPapa(null);
                    raiz = beta;
                }
                else
                {
                    beta.cuelga(alfa.getPapa());
                }
                
                if(b!=null)
                {
                    b.cuelga(alfa,'D');
                }
                else
                {
                    alfa.setDer(null);
                }
                gamma.cuelga(beta,'D');
                alfa.cuelga(beta,'I');
                actualizarFe(raiz);
                return beta;
            }
        }
    }
            
}
