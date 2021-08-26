public class ejemplo {
    
public final class Grafico extends Applet{
    BoundingBox limites = new BoundingBox(new Point3d(-100, -100, -100), new Point3d(100,100,100));
 
    public Grafico(){
        setLayout(new BorderLayout());
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add("Center",canvas3D);
        //creamos la escena y compilamos
        BranchGroup escena=crearEscena();
        escena.compile();
        //creamos el universo y acomodamos la camara
        SimpleUniverse universo=new SimpleUniverse(canvas3D);
        universo.getViewingPlatform().setNominalViewingTransform();
        //movemos la camara
        moverCamara(universo);
        //agregamos la scena a nuestro universo
        universo.addBranchGraph(escena);
    }
 
    public BranchGroup crearEscena(){
        BranchGroup escena = new BranchGroup();
        escena.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        escena.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        escena.addChild(dibujaFondo());
        escena.addChild(dibujaBola());
        return escena;
    }
 
    public void moverCamara(SimpleUniverse universo){
        TransformGroup perspectiva = universo.getViewingPlatform().getViewPlatformTransform();
        Transform3D transformPerspectiva = new Transform3D();
        transformPerspectiva.lookAt(new Point3d(0, 2, 2), new Point3d(0, 2, 0), new Vector3d(0, 1, 0));
        transformPerspectiva.invert();
        perspectiva.setTransform(transformPerspectiva);
    }
 
    private TransformGroup dibujaFondo() {
        TransformGroup fondo = new TransformGroup();
        TextureLoader cargadorCielo = new TextureLoader("img/fondo.jpg", this);
        Background back = new Background();
        back.setImage(cargadorCielo.getImage());
        back.setImageScaleMode(Background.SCALE_FIT_ALL);
        back.setApplicationBounds(limites);
        fondo.addChild(back);
        return fondo;
    }
 
    private BranchGroup dibujaBola() {
        BranchGroup bola = new BranchGroup();
 
        int paratextura=Primitive.GENERATE_NORMALS+Primitive.GENERATE_TEXTURE_COORDS;
        Sphere forma = new Sphere(0.7f,paratextura,crearApariencia());
 
        Color3f lightColor = new Color3f (1.0f, 1.0f, 1.0f);
        Vector3f light1Direction = new Vector3f (0.0f, 0.0f, -1f);
 
        DirectionalLight lightA = new DirectionalLight(lightColor, light1Direction);
        lightA.setInfluencingBounds(new BoundingSphere());
 
        AmbientLight ambientLightNode = new AmbientLight (lightColor);
     ambientLightNode.setInfluencingBounds (limites);
 
        TransformGroup tg=new TransformGroup();
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
 
        Transform3D t3d=new Transform3D();
        Vector3f vector = new Vector3f(0.0f,2.0f,-2.0f);
        t3d.setTranslation(vector);
        tg.setTransform(t3d);
        tg.addChild(forma);
        bola.addChild(tg);
        bola.addChild(lightA);
        bola.addChild(ambientLightNode);
 
        //rotar
        MouseRotate myMouseRotate = new MouseRotate();
        myMouseRotate.setTransformGroup(tg);
        myMouseRotate.setSchedulingBounds(new BoundingSphere());
        bola.addChild(myMouseRotate);
        //zoom
        MouseZoom myMouseZoom = new MouseZoom();
        myMouseZoom.setTransformGroup(tg);
        myMouseZoom.setSchedulingBounds(new BoundingSphere());
        bola.addChild(myMouseZoom);
        return bola;
    }
 
    public Appearance crearApariencia(){
        TextureLoader loader = new TextureLoader("img/text1.jpg","INTENSITY",new Container());
        Texture texture = loader.getTexture();
        Appearance apariencia=new Appearance();
        apariencia.setTexture(texture);
        Material material = new Material();
        apariencia.setMaterial(material);
 
        return apariencia;
    }
 
}
}
