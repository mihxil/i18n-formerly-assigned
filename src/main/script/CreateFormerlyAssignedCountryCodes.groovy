import org.jsoup.Jsoup

import java.time.Year
import java.util.regex.Pattern
import com.sun.codemodel.*


void createClass(String path) {

    def url = "https://en.wikipedia.org/wiki/ISO_3166-3"
    def html =  Jsoup.parse(new URL(url), 10000)

    JCodeModel model = new JCodeModel()



    model._class("org.meeuw.i18n.formerlyassigned.FormerlyAssignedCountryCode", ClassType.ENUM).with {

        javadoc()
                .append("This class is automatically generated from <a href=\"" + url +"\">" + url + "</a>.")
                .append("It defines all known <a href=\"https://www.iso.org/standard/63547.html\">ISO 3166-3</a> codes for former countries")

        JFieldVar name = field(JMod.PRIVATE | JMod.FINAL, String.class, "nameInEnglish")
        JFieldVar locale = field(JMod.PRIVATE | JMod.FINAL, Locale.class, "locale")
        //JFieldVar currency = field(JMod.PRIVATE | JMod.FINAL, Currency.class, "currency")
        JClass stringList = model.ref(List.class).narrow(String.class)

        JFieldVar formerCodes = field(JMod.PRIVATE | JMod.FINAL, stringList, "formerCodes")

        JClass year = model.ref(Year.class)
        JClass rangeClass = model.ref("ValidityRange")
        JClass thisClass = model.ref("FormerlyAssignedCountryCode")

        JFieldVar validity = field(JMod.PRIVATE | JMod.FINAL, rangeClass, "validity")


        constructor(JMod.PRIVATE).with {
            body().with {
                assign(JExpr._this().ref(name), param(String.class, "name"))
                assign(JExpr._this().ref(locale), param(Locale.class, "locale"))
                //assign(JExpr._this().ref(currency), param(Currency.class, "currency"))
                assign(JExpr._this().ref(formerCodes), param(stringList, "formerCodes"))
                assign(JExpr._this().ref(validity), param(rangeClass, "validity"))
            }
        }

        method(JMod.PUBLIC, String.class, "getName").with {
            body()._return(name)
            javadoc().append("Returns the name of this region (in english)")

        }
        method(JMod.PUBLIC, Locale.class, "toLocale").with {
            body()._return(locale)
            javadoc().append("Returns the locale associated with this country. This may not always be possible, but many countries are associated with a language, and other locale specific settings")
        }

        method(JMod.PUBLIC, String.class, "getISO3166_3_Code").with {
            body()._return(JExpr.direct("name()"))
            javadoc().append("Returns the ISO 3166-3 code for this former country")
        }


        /*method(JMod.PUBLIC, Currency.class, "getCurrency").with {
            annotate(Override.class)
            body()._return(currency)
        }
*/
        method(JMod.PUBLIC, stringList, "getFormerCodes").with {
            body()._return(formerCodes)
            javadoc().append("Returns a list of all official codes this country <em>used to have</em>")
        }

        method(JMod.PUBLIC, rangeClass, "getValidity").with {
            body()._return(validity)
        }

        method(JMod.PUBLIC | JMod.STATIC, thisClass, "getByCode").with {
            javadoc().append("Gets by the former country by its 4 letter code. It will also try to match by the original 2 codes. This may not be unique, and then the most recently assigned one will match")
            param(String.class, "code")
            body().directStatement("""
            for (FormerlyAssignedCountryCode proposal : values()) {
               if(proposal.name().equals(code)) {
                  return proposal;
               }

            }
            Year until = Year.of(1);
            FormerlyAssignedCountryCode proposal = null;
            for (FormerlyAssignedCountryCode v : values()) {
               for (String formerCode : v.getFormerCodes()) {
                  if(formerCode.equals(code) & v.getValidity().upperEndpoint().isAfter(until)) {
                     proposal = v;
                   }
               }
            }
            return proposal;
""");
        }

        println "Slurping " + url + " table "
        table = 0
        html.body().select("table, tr").each {
            if (it.nodeName() == 'table') {
                table++
                return
            }
            if ( table != 1) {
                return
            }
            def tds = it.select("td")
            if (tds.size() == 0) {
                return
            }
            def ths = it.select("th")

            String td0 = tds[0].select("a")[0].text(); // name
            String td0id = tds[0].id()

            if (td0id.length() > 0) {
                String[] td2 = tds[2].text().trim().split("[^\\d]", 3) // validity
                Integer year1 = Integer.parseInt(td2[0]);
                Integer year2 = Integer.parseInt(td2[1]);
                String th0 = ths[0].select("span")[0].text() // code
                String td3 = tds[3].text()  // new country name

                JInvocation asList = model.ref(Arrays.class)
                        .staticInvoke("asList")
                tds[1].select("span").each {
                    String code = it.text()
                    if (Pattern.compile("(?i)^[a-z0-9]+\$").matcher(code).matches()) {
                        asList.arg(code)
                    }
                } // former codes

                enumConstant(th0).with {
                    javadoc().append("<a href=\"" + url + "#" + td0id + "\">" + td0 + "</a> (" + td3 +")")
                    arg(JExpr.lit(td0))
                    //arg(JExpr._null())
                    arg(JExpr._null())
                    arg(asList)
                    arg(rangeClass.staticInvoke("closed")
                            .arg(year.staticInvoke("of").arg(JExpr.lit(year1)))
                            .arg(year.staticInvoke("of").arg(JExpr.lit(year2)))
                    )
                }
            }
        }

        methods();
    }

    File dir = new File(path)
    dir.mkdirs()

    try {
        model.build(dir)
    } catch (Exception e) {
        println e
    }
}
String path = properties['path']
if (path == null) {
    path = "/tmp"
}

createClass(path)
