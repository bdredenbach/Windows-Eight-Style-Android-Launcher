"""Build the research PDF from the adjacent Markdown and source ledger.

Usage: python3 build_pdf.py /absolute/output.pdf
Requires ReportLab. No publisher screenshots or downloaded reference books are bundled.
"""
from pathlib import Path
import sys,re,json,html
from reportlab.pdfgen import canvas
from reportlab.platypus import BaseDocTemplate,PageTemplate,Frame,Paragraph,Spacer,PageBreak,LongTable,TableStyle,KeepTogether,Preformatted,Flowable
from reportlab.platypus.tableofcontents import TableOfContents
from reportlab.lib.styles import ParagraphStyle
from reportlab.lib import colors
from reportlab.lib.enums import TA_LEFT
from reportlab.pdfbase import pdfmetrics
from reportlab.pdfbase.ttfonts import TTFont

ROOT=Path(__file__).resolve().parent
OUT=Path(sys.argv[1] if len(sys.argv)>1 else 'Nth-Desktop-Eight-Windows-8-8.1-Research.pdf')
OUT.parent.mkdir(parents=True,exist_ok=True)
FONTS=Path('/usr/share/fonts/truetype/dejavu')
for name,file in [('Body','DejaVuSans.ttf'),('Body-Bold','DejaVuSans-Bold.ttf'),('Body-Italic','DejaVuSans-Oblique.ttf'),('Mono','DejaVuSansMono.ttf')]:
 pdfmetrics.registerFont(TTFont(name,str(FONTS/file)))
pdfmetrics.registerFontFamily('Body',normal='Body',bold='Body-Bold',italic='Body-Italic',boldItalic='Body-Bold')
PURPLE=colors.HexColor('#21164F');TEAL=colors.HexColor('#007F8B');INK=colors.HexColor('#20262C');MUTED=colors.HexColor('#55616D');LIGHT=colors.HexColor('#F1F5F6')
W,H=595.276,841.89;M=45;CW=W-2*M
sources=json.loads((ROOT/'sources.json').read_text())['sources'];urls={s['id']:s['url'] for s in sources}
chapters=[('README.md','Overview and release boundaries'),('COMPLETENESS_REVIEW.md','Completion review and corrections'),('LIVE_TILES.md','Live tiles: behavior and implementation'),('SETTINGS_ATLAS.md','PC settings and Control Panel atlas'),('UI_AUDIT.md','Whole-shell and app UI audit'),('FILE_MANAGEMENT_DETAIL.md','Windows, Explorer and Recycle Bin'),('APP_CANDIDATES.md','Modern-era app candidates'),('ADDITIONAL_APPS_AND_DESIGN.md','Additional apps and design details'),('VIDEO_OBSERVATIONS.md','Video observations and evidence limits'),('IMPLEMENTATION_PLAN.md','Implementation and acceptance plan')]
bookmarks={f:'chapter-'+str(i+1) for i,(f,_) in enumerate(chapters)};bookmarks['SOURCES.md']='sources'
styles={
 'body':ParagraphStyle('body',fontName='Body',fontSize=9.1,leading=13.7,textColor=INK,spaceAfter=7.3,allowWidows=0,allowOrphans=0),
 'h1':ParagraphStyle('h1',fontName='Body-Bold',fontSize=21,leading=26,textColor=PURPLE,spaceAfter=16,keepWithNext=True),
 'h2':ParagraphStyle('h2',fontName='Body-Bold',fontSize=13,leading=17,textColor=TEAL,spaceBefore=14,spaceAfter=8,keepWithNext=True),
 'h3':ParagraphStyle('h3',fontName='Body-Bold',fontSize=10.1,leading=14,textColor=INK,spaceBefore=10,spaceAfter=6,keepWithNext=True),
 'cell':ParagraphStyle('cell',fontName='Body',fontSize=8,leading=11.4,textColor=INK,spaceAfter=0,splitLongWords=True),
 'th':ParagraphStyle('th',fontName='Body-Bold',fontSize=8,leading=11.4,textColor=colors.white),
 'code':ParagraphStyle('code',fontName='Mono',fontSize=7.5,leading=10.6,textColor=INK,backColor=LIGHT,borderPadding=8,spaceAfter=10),
 'small':ParagraphStyle('small',fontName='Body',fontSize=8,leading=11.8,textColor=MUTED,spaceAfter=6),
 'source':ParagraphStyle('source',fontName='Body',fontSize=8,leading=11.6,textColor=INK,spaceAfter=4,splitLongWords=True),
}

def norm(s):
 for a,b in [('—','-'),('–','-'),('‑','-'),('→',' -> '),('“','"'),('”','"'),('’',"'"),('‘',"'"),('\u00a0',' ')]:s=s.replace(a,b)
 return s

def rich(s):
 s=html.escape(norm(s));tokens=[]
 def hold(t):tokens.append(t);return f'ZZTOKEN{len(tokens)-1}ZZ'
 def link(m):
  label,url=m.group(1),html.unescape(m.group(2));target=('#'+bookmarks[url]) if url in bookmarks else url
  if not target.startswith(('http','#')):target='https://github.com/bdredenbach/Windows-Eight-Style-Android-Launcher/blob/main/docs/eight-research/'+target
  return hold(f'<link href="{html.escape(target,quote=True)}" color="#007F8B">{label}</link>')
 s=re.sub(r'\[([^\]]+)\]\(([^\s]+)\)',link,s)
 s=re.sub(r'`([^`]+)`',lambda m:hold('<font name="Mono" size="7.9">'+m[1]+'</font>'),s)
 s=re.sub(r'\*\*(.+?)\*\*',r'<b>\1</b>',s)
 s=re.sub(r'(?<!\*)\*([^*]+)\*(?!\*)',r'<i>\1</i>',s)
 s=re.sub(r'\[([A-Z]\d{2})\]',lambda m:hold(f'<link href="{html.escape(urls[m[1]],quote=True)}" color="#007F8B">[{m[1]}]</link>') if m[1] in urls else m[0],s)
 for i,t in enumerate(tokens):s=s.replace(f'ZZTOKEN{i}ZZ',t)
 return s

def p(s,style='body'):return Paragraph(rich(s),styles[style])

class ResearchDoc(BaseDocTemplate):
 def __init__(self,path):
  super().__init__(str(path),pagesize=(W,H),leftMargin=M,rightMargin=M,topMargin=54,bottomMargin=48,title='Nth Desktop Eight - Windows 8 and 8.1 Research',author='Nth Experience',pageCompression=1)
  self.addPageTemplates(PageTemplate('normal',[Frame(M,48,CW,H-103,id='body',leftPadding=0,rightPadding=0,topPadding=0,bottomPadding=0)],onPage=self.decorate))
 def decorate(self,c,doc):
  if doc.page==1:return
  c.setFillColor(PURPLE);c.setFont('Body-Bold',8);c.drawString(M,H-29,'NTH DESKTOP EIGHT')
  c.setFont('Body',7.7);c.setFillColor(MUTED);c.drawRightString(W-M,H-29,'Windows 8 / 8.1 - expanded research')
  c.setStrokeColor(colors.HexColor('#D7DEE1'));c.line(M,H-37,W-M,H-37)
  c.setFont('Body',7);c.drawString(M,27,'6 November 2026 | Baseline 0.4.0 | Findings and proposals')
  c.drawRightString(W-M,27,str(doc.page))
 def afterFlowable(self,f):
  if isinstance(f,Paragraph) and hasattr(f,'chapter_key'):
   txt=f.getPlainText();self.canv.bookmarkPage(f.chapter_key);self.canv.addOutlineEntry(txt,f.chapter_key,0,False);self.notify('TOCEntry',(0,txt,self.page,f.chapter_key))

class Cover(Flowable):
 def __init__(self):super().__init__();self.width=CW;self.height=714
 def draw(self):
  c=self.canv;c.setFillColor(PURPLE);c.rect(-M,-4,W,724,fill=1,stroke=0)
  c.setFillColor(colors.HexColor('#6ED4DC'));c.setFont('Body-Bold',12);c.drawString(0,654,'NTH EXPERIENCE / RESEARCH EDITION')
  c.setFillColor(colors.white);c.setFont('Body-Bold',40);c.drawString(0,568,'Windows 8');c.drawString(0,514,'and Windows 8.1')
  c.setFont('Body',21);c.drawString(0,457,'Nth Desktop Eight')
  c.setFont('Body',13);c.drawString(0,420,'Usage, live tiles, settings, desktop and apps')
  tiles=[('Live tiles','Useful content + motion','#008C98'),('Settings','Page and workflow atlas','#4361AD'),('Desktop','Windows + file management','#80318B')]
  for i,(title,sub,color) in enumerate(tiles):
   x=i*171;c.setFillColor(colors.HexColor(color));c.rect(x,258,162,113,fill=1,stroke=0);c.setFillColor(colors.white);c.setFont('Body-Bold',14);c.drawString(x+13,332,title);c.setFont('Body',8);c.drawString(x+13,288,sub)
  c.setFillColor(colors.white);c.setFont('Body-Bold',12);c.drawString(0,196,f'{len(sources)} references. A reviewed and expanded investigation.')
  c.setFont('Body',10);c.drawString(0,166,'Microsoft period sources, publisher/OEM guides, selected footage,')
  c.drawString(0,149,'original tutorials and an audit of the current launcher code.')
  c.setFillColor(colors.HexColor('#BBDDE4'));c.setFont('Body',9);c.drawString(0,94,'6 November 2026  |  App baseline 0.4.0')
  c.drawString(0,73,'Research and proposed work. New findings are identified; no APK is included.')

def table(lines):
 rows=[[s.strip() for s in l.strip().strip('|').split('|')] for l in lines]
 rows=[r for r in rows if not all(re.fullmatch(r':?-+:?',x or '-') for x in r)]
 n=len(rows[0]);ratios={2:[.30,.70],3:[.22,.39,.39],4:[.18,.28,.28,.26],5:[.15,.22,.21,.21,.21]}.get(n,[1/n]*n)
 cells=[[p(s,'th' if i==0 else 'cell') for s in row] for i,row in enumerate(rows)]
 t=LongTable(cells,colWidths=[CW*r for r in ratios],repeatRows=1,hAlign='LEFT')
 t.setStyle(TableStyle([('BACKGROUND',(0,0),(-1,0),PURPLE),('VALIGN',(0,0),(-1,-1),'TOP'),('ROWBACKGROUNDS',(0,1),(-1,-1),[colors.white,LIGHT]),('LEFTPADDING',(0,0),(-1,-1),8),('RIGHTPADDING',(0,0),(-1,-1),8),('TOPPADDING',(0,0),(-1,-1),7),('BOTTOMPADDING',(0,0),(-1,-1),7),('LINEBELOW',(0,0),(-1,0),1,TEAL),('LINEBELOW',(0,1),(-1,-1),.3,colors.HexColor('#D9E2E5'))]))
 return [t,Spacer(1,10)]

def parse(md,title,key):
 lines=md.splitlines();flow=[];i=0
 h=p(title,'h1');h.chapter_key=key;flow.append(h)
 while i<len(lines):
  line=lines[i].strip()
  if not line or line.startswith('<!--') or re.match(r'^\[[A-Z]\d{2}\]:',line):i+=1;continue
  if line.startswith('# '):i+=1;continue
  if line.startswith('### '):flow.append(p(line[4:],'h3'));i+=1;continue
  if line.startswith('## '):flow.append(p(line[3:],'h2'));i+=1;continue
  if line.startswith('|'):
   end=i
   while end<len(lines) and lines[end].strip().startswith('|'):end+=1
   flow.extend(table(lines[i:end]));i=end;continue
  if line.startswith('```'):
   end=i+1
   while end<len(lines) and not lines[end].startswith('```'):end+=1
   flow.append(Preformatted(norm('\n'.join(lines[i+1:end])),styles['code']));i=end+1;continue
  if re.match(r'^[-*] ',line) or re.match(r'^\d+\. ',line):
   flow.append(p(line));i+=1;continue
  block=[line];i+=1
  while i<len(lines) and lines[i].strip() and not re.match(r'^(#{1,3} |\||```|[-*] |\d+\. |<!--|\[[A-Z]\d{2}\]:)',lines[i].strip()):block.append(lines[i].strip());i+=1
  flow.append(p(' '.join(block)))
 return flow

story=[Cover(),PageBreak()]
story.append(p('Contents','h1'))
story.append(p('Start with the completion review for corrections and additions. The original live-tile and app specifications are retained, with expanded settings and file-management detail. Source records distinguish direct review, selected samples and unavailable media.'))
toc=TableOfContents();toc.levelStyles=[ParagraphStyle('TOC',fontName='Body',fontSize=10.5,leading=17,textColor=PURPLE,leftIndent=0,firstLineIndent=0,spaceBefore=8)];story.extend([toc,PageBreak()])
for idx,(f,title) in enumerate(chapters):
 if idx:story.append(PageBreak())
 story.extend(parse((ROOT/f).read_text(),title,bookmarks[f]))
story.append(PageBreak());h=p('Source ledger','h1');h.chapter_key='sources';story.append(h)
story.append(p('62 reference records: 48 carried forward and 14 added in the completion review. Access notes are part of the evidence. A failed-access or transcript-only record is not a claim of watched footage or inspected screenshots.'))
for s in sources:
 title=p(f"{s['id']} - {s['title']}",'h3')
 info=p(f"**{s['publisher']}** | {s['evidence_type']} | Accessed {s['accessed']}",'source')
 scope=p(s['scope'],'source');note=p(s['review_note'],'source')
 url=Paragraph(f'<link href="{html.escape(s["url"],quote=True)}" color="#007F8B">{html.escape(s["url"])}</link>',styles['small'])
 story.append(KeepTogether([title,info,scope,note,url,Spacer(1,5)]))
doc=ResearchDoc(OUT);doc.multiBuild(story)
print(str(OUT.resolve()))
