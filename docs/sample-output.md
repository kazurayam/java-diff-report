## Sample diff report of 2 HTML files
### Sources
- original : ~/github/java-diff-report/src/test/fixtures/left.html
- revised : ~/github/java-diff-report/src/test/fixtures/right.html

### Stats
 **DIFFERENT** at 2024-02-05 10:39:05
- inserted rows : 3
- deleted rows  : 1
- changed rows  : 24
- equal rows:   : 45

### Detail
|row#|S|original|revised|
|----|-|--------|-------|
| 1 |   | &lt;!DOCTYPE html&gt; | &lt;!DOCTYPE html&gt; |
| 2 | D | <span style="color:red; font-weight:bold; background-color:#ffeef0">&lt;!--  --&gt;</span> |  |
| 3 |   | &lt;html lang="ja"&gt;&lt;head&gt;&lt;meta http-equiv="Content-Type" content="text/html; charset=UTF-8"&gt; | &lt;html lang="ja"&gt;&lt;head&gt;&lt;meta http-equiv="Content-Type" content="text/html; charset=UTF-8"&gt; |
| 4 |   |     &lt;meta name="viewport" content="width=device-width, initial-scale=1"&gt; |     &lt;meta name="viewport" content="width=device-width, initial-scale=1"&gt; |
| 5 | C |     &lt;link href="./<span style="color:red; font-weight:bold; background-color:#ffeef0">left_files</span>/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous"&gt; |     &lt;link href="./<span style="color:green; font-weight:bold; background-color:#e6ffec">right_files</span>/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous"&gt; |
| 6 | C |     &lt;link rel="stylesheet" href="./<span style="color:red; font-weight:bold; background-color:#ffeef0">left_files</span>/bootstrap-icons.css"&gt; |     &lt;link rel="stylesheet" href="./<span style="color:green; font-weight:bold; background-color:#e6ffec">right_files</span>/bootstrap-icons.css"&gt; |
| 7 |   |     &lt;title&gt;My Admin&lt;/title&gt; |     &lt;title&gt;My Admin&lt;/title&gt; |
| 8 |   |   &lt;style&gt;:is([id*='google_ads_iframe'],[id*='taboola-'],.taboolaHeight,.taboola-placeholder,#credential_picker_container,#credentials-picker-container,#credential_picker_iframe,[id*='google-one-tap-iframe'],#google-one-tap-popup-container,.google-one-tap-modal-div,#amp_floatingAdDiv,#ez-content-blocker-container) {display:none!important;min-height:0!important;height:0!important;}&lt;/style&gt;&lt;/head&gt; |   &lt;style&gt;:is([id*='google_ads_iframe'],[id*='taboola-'],.taboolaHeight,.taboola-placeholder,#credential_picker_container,#credentials-picker-container,#credential_picker_iframe,[id*='google-one-tap-iframe'],#google-one-tap-popup-container,.google-one-tap-modal-div,#amp_floatingAdDiv,#ez-content-blocker-container) {display:none!important;min-height:0!important;height:0!important;}&lt;/style&gt;&lt;/head&gt; |
|...| | | |
| 11 |   |         &lt;nav class="navbar navbar-expand-md navbar-dark bg-primary"&gt; |         &lt;nav class="navbar navbar-expand-md navbar-dark bg-primary"&gt; |
| 12 |   |             &lt;div class="container-fluid"&gt; |             &lt;div class="container-fluid"&gt; |
| 13 | C |               &lt;a class="navbar-brand" href="https://kazurayam.github.io/VisualInspectionOfCssAndJs/demo/MyAdmin_visual_inspection_twins/<span style="color:red; font-weight:bold; background-color:#ffeef0">20220221_183058</span>/objects/<span style="color:red; font-weight:bold; background-color:#ffeef0">75f6fc61a4a7beced95470f5ae881e533c3a2d8f</span>.html#"&gt;My Admin&lt;/a&gt; |               &lt;a class="navbar-brand" href="https://kazurayam.github.io/VisualInspectionOfCssAndJs/demo/MyAdmin_visual_inspection_twins/<span style="color:green; font-weight:bold; background-color:#e6ffec">20220221_183122</span>/objects/<span style="color:green; font-weight:bold; background-color:#e6ffec">5d7e467a45a85329612d1f0694f9d726bc14226d</span>.html#"&gt;My Admin&lt;/a&gt; |
| 14 |   |               &lt;button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation"&gt; |               &lt;button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation"&gt; |
| 15 |   |                 &lt;span class="navbar-toggler-icon"&gt;&lt;/span&gt; |                 &lt;span class="navbar-toggler-icon"&gt;&lt;/span&gt; |
|...| | | |
| 18 |   |                 &lt;ul class="navbar-nav"&gt; |                 &lt;ul class="navbar-nav"&gt; |
| 19 |   |                   &lt;li class="nav-item"&gt; |                   &lt;li class="nav-item"&gt; |
| 20 | C |                     &lt;a class="nav-link active" aria-current="page" href="https://kazurayam.github.io/VisualInspectionOfCssAndJs/demo/MyAdmin_visual_inspection_twins/<span style="color:red; font-weight:bold; background-color:#ffeef0">20220221_183058</span>/objects/<span style="color:red; font-weight:bold; background-color:#ffeef0">75f6fc61a4a7beced95470f5ae881e533c3a2d8f</span>.html#"&gt;Home&lt;/a&gt; |                     &lt;a class="nav-link active" aria-current="page" href="https://kazurayam.github.io/VisualInspectionOfCssAndJs/demo/MyAdmin_visual_inspection_twins/<span style="color:green; font-weight:bold; background-color:#e6ffec">20220221_183122</span>/objects/<span style="color:green; font-weight:bold; background-color:#e6ffec">5d7e467a45a85329612d1f0694f9d726bc14226d</span>.html#"&gt;Home&lt;/a&gt; |
| 21 |   |                   &lt;/li&gt; |                   &lt;/li&gt; |
| 22 |   |                   &lt;li class="nav-item"&gt; |                   &lt;li class="nav-item"&gt; |
| 23 | C |                     &lt;a class="nav-link" href="https://kazurayam.github.io/VisualInspectionOfCssAndJs/demo/MyAdmin_visual_inspection_twins/<span style="color:red; font-weight:bold; background-color:#ffeef0">20220221_183058</span>/objects/<span style="color:red; font-weight:bold; background-color:#ffeef0">75f6fc61a4a7beced95470f5ae881e533c3a2d8f</span>.html#"&gt;News&lt;/a&gt; |                     &lt;a class="nav-link" href="https://kazurayam.github.io/VisualInspectionOfCssAndJs/demo/MyAdmin_visual_inspection_twins/<span style="color:green; font-weight:bold; background-color:#e6ffec">20220221_183122</span>/objects/<span style="color:green; font-weight:bold; background-color:#e6ffec">5d7e467a45a85329612d1f0694f9d726bc14226d</span>.html#"&gt;News&lt;/a&gt; |
| 24 |   |                   &lt;/li&gt; |                   &lt;/li&gt; |
| 25 | I |  | <span style="color:green; font-weight:bold; background-color:#e6ffec">                  &lt;li class="nav-item"&gt;</span> |
| 26 | I |  | <span style="color:green; font-weight:bold; background-color:#e6ffec">                    &lt;a class="nav-link" href="https://kazurayam.github.io/VisualInspectionOfCssAndJs/demo/MyAdmin_visual_inspection_twins/20220221_183122/objects/5d7e467a45a85329612d1f0694f9d726bc14226d.html#"&gt;Docs&lt;/a&gt;</span> |
| 27 | I |  | <span style="color:green; font-weight:bold; background-color:#e6ffec">                  &lt;/li&gt;</span> |
| 28 |   |                 &lt;/ul&gt; |                 &lt;/ul&gt; |
| 29 |   |               &lt;/div&gt; |               &lt;/div&gt; |
|...| | | |
| 35 |   |             &lt;section class="col-md-4 mb-5"&gt; |             &lt;section class="col-md-4 mb-5"&gt; |
| 36 |   |                 &lt;div class="list-group"&gt; |                 &lt;div class="list-group"&gt; |
| 37 | C |                     &lt;a href="https://kazurayam.github.io/VisualInspectionOfCssAndJs/demo/MyAdmin_visual_inspection_twins/<span style="color:red; font-weight:bold; background-color:#ffeef0">20220221_183058</span>/objects/<span style="color:red; font-weight:bold; background-color:#ffeef0">75f6fc61a4a7beced95470f5ae881e533c3a2d8f</span>.html#" class="list-group-item list-group-item-<span style="color:red; font-weight:bold; background-color:#ffeef0">action</span> <span style="color:red; font-weight:bold; background-color:#ffeef0">active" aria-current="true"&gt;Profile&lt;</span>/a&gt; |                     &lt;a href="https://kazurayam.github.io/VisualInspectionOfCssAndJs/demo/MyAdmin_visual_inspection_twins/<span style="color:green; font-weight:bold; background-color:#e6ffec">20220221_183122</span>/objects/<span style="color:green; font-weight:bold; background-color:#e6ffec">5d7e467a45a85329612d1f0694f9d726bc14226d</span>.html#" class="list-group-item list-group-item-<span style="color:green; font-weight:bold; background-color:#e6ffec">action"&gt;Access</span> <span style="color:green; font-weight:bold; background-color:#e6ffec">Stats&lt;</span>/a&gt; |
| 38 | C |                     &lt;a href="https://kazurayam.github.io/VisualInspectionOfCssAndJs/demo/MyAdmin_visual_inspection_twins/<span style="color:red; font-weight:bold; background-color:#ffeef0">20220221_183058</span>/objects/<span style="color:red; font-weight:bold; background-color:#ffeef0">75f6fc61a4a7beced95470f5ae881e533c3a2d8f</span>.html#" class="list-group-item list-group-item-<span style="color:red; font-weight:bold; background-color:#ffeef0">action"&gt;Access Stats&lt;</span>/a&gt; |                     &lt;a href="https://kazurayam.github.io/VisualInspectionOfCssAndJs/demo/MyAdmin_visual_inspection_twins/<span style="color:green; font-weight:bold; background-color:#e6ffec">20220221_183122</span>/objects/<span style="color:green; font-weight:bold; background-color:#e6ffec">5d7e467a45a85329612d1f0694f9d726bc14226d</span>.html#" class="list-group-item list-group-item-<span style="color:green; font-weight:bold; background-color:#e6ffec">action"&gt;Settings&lt;</span>/a&gt; |
| 39 | C |                     &lt;a href="https://kazurayam.github.io/VisualInspectionOfCssAndJs/demo/MyAdmin_visual_inspection_twins/<span style="color:red; font-weight:bold; background-color:#ffeef0">20220221_183058</span>/objects/<span style="color:red; font-weight:bold; background-color:#ffeef0">75f6fc61a4a7beced95470f5ae881e533c3a2d8f</span>.html#" class="list-group-item list-group-item-<span style="color:red; font-weight:bold; background-color:#ffeef0">action"&gt;Settings&lt;</span>/a&gt; |                     &lt;a href="https://kazurayam.github.io/VisualInspectionOfCssAndJs/demo/MyAdmin_visual_inspection_twins/<span style="color:green; font-weight:bold; background-color:#e6ffec">20220221_183122</span>/objects/<span style="color:green; font-weight:bold; background-color:#e6ffec">5d7e467a45a85329612d1f0694f9d726bc14226d</span>.html#" class="list-group-item list-group-item-<span style="color:green; font-weight:bold; background-color:#e6ffec">action active" aria-current="true"&gt;Profile&lt;</span>/a&gt; |
| 40 |   |                 &lt;/div&gt; |                 &lt;/div&gt; |
| 41 |   |             &lt;/section&gt; |             &lt;/section&gt; |
|...| | | |
| 47 |   |                     &lt;/button&gt; |                     &lt;/button&gt; |
| 48 |   |                 &lt;/header&gt; |                 &lt;/header&gt; |
| 49 | C |                 &lt;p&gt;&lt;img src="./<span style="color:red; font-weight:bold; background-color:#ffeef0">left_files</span>/umineko-1960x1960.jpg" alt="umineko" class="rounded-circle img-fluid ps-5 pe-5"&gt;&lt;/p&gt; |                 &lt;p&gt;&lt;img src="./<span style="color:green; font-weight:bold; background-color:#e6ffec">right_files</span>/umineko-1960x1960.jpg" alt="umineko" class="rounded-circle img-fluid ps-5 pe-5"&gt;&lt;/p&gt; |
| 50 | C |                 <span style="color:red; font-weight:bold; background-color:#ffeef0">&lt;!-- </span>&lt;h2&gt;&lt;span <span style="color:red; font-weight:bold; background-color:#ffeef0">id="clock"&gt;&lt;</span>/span&gt; UTC&lt;/h2&gt;<span style="color:red; font-weight:bold; background-color:#ffeef0"> --&gt;</span> |                 &lt;h2&gt;&lt;span <span style="color:green; font-weight:bold; background-color:#e6ffec">id="clock"&gt;2024</span>/<span style="color:green; font-weight:bold; background-color:#e6ffec">2/2 13:51:41&lt;/</span>span&gt; UTC&lt;/h2&gt; |
| 51 |   |             &lt;/section&gt; |             &lt;/section&gt; |
| 52 |   |         &lt;/section&gt; |         &lt;/section&gt; |
|...| | | |
| 55 |   |         (c) kazurayam.com |         (c) kazurayam.com |
| 56 |   |     &lt;/footer&gt; |     &lt;/footer&gt; |
| 57 | C |     &lt;script src="./<span style="color:red; font-weight:bold; background-color:#ffeef0">left_files</span>/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"&gt; |     &lt;script src="./<span style="color:green; font-weight:bold; background-color:#e6ffec">right_files</span>/bootstrap.bundle.min.js" integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj" crossorigin="anonymous"&gt; |
| 58 |   |     &lt;/script&gt; |     &lt;/script&gt; |
| 59 | C |     &lt;script src="./<span style="color:red; font-weight:bold; background-color:#ffeef0">left_files</span>/jquery.js"&gt;&lt;/script&gt; |     &lt;script src="./<span style="color:green; font-weight:bold; background-color:#e6ffec">right_files</span>/jquery.js"&gt;&lt;/script&gt; |
| 60 |   |     &lt;script&gt; |     &lt;script&gt; |
| 61 | C | <span style="color:red; font-weight:bold; background-color:#ffeef0">      </span>/**<span style="color:red; font-weight:bold; background-color:#ffeef0"> | <span style="color:green; font-weight:bold; background-color:#e6ffec">        </span>/**<span style="color:green; font-weight:bold; background-color:#e6ffec"> |
| 62 | C |        </span>* display the current timestamp in the format of "07:22:13"<span style="color:red; font-weight:bold; background-color:#ffeef0"> |          </span>* display the current timestamp in the format of "07:22:13"<span style="color:green; font-weight:bold; background-color:#e6ffec"> |
| 63 | C |        </span>*/<span style="color:red; font-weight:bold; background-color:#ffeef0"> |          </span>*/<span style="color:green; font-weight:bold; background-color:#e6ffec"> |
| 64 | C |       </span>$(document).ready(function() {<span style="color:red; font-weight:bold; background-color:#ffeef0"> |         </span>$(document).ready(function() {<span style="color:green; font-weight:bold; background-color:#e6ffec"> |
| 65 | C |         </span>var m = new Date();<span style="color:red; font-weight:bold; background-color:#ffeef0"> |           </span>var m = new Date();<span style="color:green; font-weight:bold; background-color:#e6ffec"> |
| 66 | C |         </span>var dateString = m.getUTCFullYear() +"/"+ (m.getUTCMonth()+1) +"/"+<span style="color:red; font-weight:bold; background-color:#ffeef0"> |           </span>var dateString = m.getUTCFullYear() +"/"+ (m.getUTCMonth()+1) +"/"+<span style="color:green; font-weight:bold; background-color:#e6ffec"> |
| 67 | C |                          </span>m.getUTCDate() + " " + m.getUTCHours() + ":" +<span style="color:red; font-weight:bold; background-color:#ffeef0"> |                            </span>m.getUTCDate() + " " + m.getUTCHours() + ":" +<span style="color:green; font-weight:bold; background-color:#e6ffec"> |
| 68 | C |                         </span>m.getUTCMinutes() + ":" + m.getUTCSeconds();<span style="color:red; font-weight:bold; background-color:#ffeef0"> |                           </span>m.getUTCMinutes() + ":" + m.getUTCSeconds();<span style="color:green; font-weight:bold; background-color:#e6ffec"> |
| 69 | C |         </span>console.log(dateString)<span style="color:red; font-weight:bold; background-color:#ffeef0"> |           </span>console.log(dateString)<span style="color:green; font-weight:bold; background-color:#e6ffec"> |
| 70 | C |         </span>$('#clock').text(dateString);<span style="color:red; font-weight:bold; background-color:#ffeef0"> |           </span>$('#clock').text(dateString);<span style="color:green; font-weight:bold; background-color:#e6ffec"> |
| 71 | C |       </span>})<span style="color:red; font-weight:bold; background-color:#ffeef0"> |         </span>})<span style="color:green; font-weight:bold; background-color:#e6ffec"> |
| 72 | C |     </span>&lt;/script&gt; |       </span>&lt;/script&gt; |
| 73 |   | &lt;/body&gt;&lt;/html&gt; | &lt;/body&gt;&lt;/html&gt; |