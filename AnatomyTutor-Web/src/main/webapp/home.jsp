<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<s:layout-render name="/layout.jsp" titlekey="home.titlekey">
    <s:layout-component name="header">
        <s:useActionBean var="ab" beanclass="cz.muni.fi.anatomytutor.web.HomeActionBean"/>
        <c:set var="categories" scope="request" value="${ab.categories}"/>
    </s:layout-component>
    <s:layout-component name="body">
        <s:useActionBean var="ab" beanclass="cz.muni.fi.anatomytutor.web.HomeActionBean"/>
        <div data-ng-view="" id="ng-view" ng-animate="{enter: 'view-enter'}" ng-class="vip() && 'vip'" class="ng-scope">
            <center class="map-container welcome-page jumbotron ng-scope">
                <h1>Anatomie člověka</h1>
                <div class="col-lg-8 col-sm-11 col-12">
                    <div class="flatblock block-welcome_page">
                        <div class="flatblock-content">je aplikace na procvičování znalosti lidské anatomie.
                            Aplikace přizpůsobuje obtížnost otázek znalostem studenta.</div>
                    </div>
                    <a href="${pageContext.request.contextPath}/practice" class="btn btn-primary btn-lg btn-huge">Začít procvičovat</a>
                </div>
            </center>
            <div class="how-it-works ng-scope">
                <h2>Jak to funguje?</h2>
                <div class="col-md-offset-3 col-md-6">

                    <div class="works-div">
                        <h3>Adaptabilní procvičování</h3>
                        Systém vás zkouší ze znalosti anatomických pojmů, přičemž chování systému je adaptabilní - podle toho, jaké máte znalosti, systém volí podobu otázek (otevřená, výběr z možností) a frekvenci opakování.
                        Systém vám tak předkládá pojmy, jejichž procvičení je pro vás nejužitečnější.
                    </div>

                    <div class="works-div">
                        <h3>Vizualizace znalostí</h3>
                        Na základě odpovědí systém odhadne vaše znalosti jednotlivých pojmů a znázorní vám mapu vašich znalostí, což vám pomůže ujasnit si, co vlastně umíte.
                    </div>

                    <div class="works-div">
                        <h3>Specializovaný systém</h3>
                        Procvičování slepých map nabízí i řada jiných výukových systémů, ty však většinou pokrývají mnoho dílčích oblastí. Náš systém se soustředí pouze na anatomii člověka, díky čemuž je pro učení v této oblasti optimalizovaný.
                    </div>

                    <div class="works-div">
                        <h3>Učení z dat</h3>
                        Systém se učí z dat od všech uživatelů. Výběr otázek je založen na těchto datech, nikoliv na znalostech experta na zeměpis, který "ví", jestli je těžší poznat Sklivec nebo Řasnaté tělísko.
                    </div>
                </div>
                <div class="clearfix"></div>
            </div>
            <div id="footer">
                Vyvíjeno na <a href="http://www.fi.muni.cz/">FI MU</a>, všechny připomínky velmi vítány:
                <span class="email contact"><a href="mailto:anatomie@greld.cz">anatomie@greld.cz</a></span>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
