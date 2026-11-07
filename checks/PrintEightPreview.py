"""Print a bounded native-test contact sheet for visual review without artifact extraction."""
from pathlib import Path
import base64,io
from PIL import Image,ImageDraw
paths=sorted(Path("screenshots/EightFidelitySmoke").glob("eight6-*.png"))
if paths:
    thumbs=[]
    for path in paths:
        image=Image.open(path).convert("RGB")
        image.thumbnail((320,640))
        tile=Image.new("RGB",(340,675),"#20202b")
        tile.paste(image,((340-image.width)//2,28))
        ImageDraw.Draw(tile).text((8,6),path.stem,fill="white")
        thumbs.append(tile)
    sheet=Image.new("RGB",(340*3,675*((len(thumbs)+2)//3)),"#20202b")
    for i,tile in enumerate(thumbs):sheet.paste(tile,((i%3)*340,(i//3)*675))
    out=io.BytesIO();sheet.save(out,format="JPEG",quality=83)
    print("EIGHT_NATIVE_QA_JPEG:"+base64.b64encode(out.getvalue()).decode("ascii"))
else:
    print("No Fidelity captures produced.")
