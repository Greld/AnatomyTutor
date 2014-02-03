function Practice(url) {
    this.url = url;
    this.question = null;
    this.practiceState = Practice.prototype.states.showing;
    this.progressBar = new ProgressBar(5);
    var self = this;
    this.whiteBlack = function() {
        /******   BLACK AND WHITE      *****/
        $("#pictureSvg svg .term, #pictureSvg svg path").css("fill", "white");
        $("#pictureSvg svg .term, #pictureSvg svg path").css("stroke", "black");
        $("#pictureSvg svg .term").attr("data-answer", "false");
    }
    this.setEvents = function() {

        self.whiteBlack();

        $("#pictureSvg svg .term").click(function(event) {
            if (self.practiceState != Practice.prototype.states.waitForAnswerInMap) {
                window.console.log("neocekavam odpoved v mape");
                return;
            }
            if (wasMoving == event.target) {
                window.console.log("klik pri posunu");
                return;
            }
            $("#pictureSvg svg .term").each(function() {
                if ($(this).is("[data-answer='true']")) {
                    $(this).attr("data-answer", "");
                    if ($(this).prop("tagName") == "polygon") {
                        $(this).attr("style", "");
                    } else {
                        $(this).css("fill", $(this).attr("data-color"));
                    }
                }
            });
            var correctAnswer = self.question.question.idInPicture;
            window.console.log("correctAnswer" + correctAnswer);
            if ($(this).attr("data-term") == correctAnswer) {
                self.progressBar.addCorrect(correctAnswer);
                self.colorTerm(correctAnswer, "green");
                window.setTimeout(function() {
                    self.loadNewQuestion();
                }, 1000);

            } else {
                self.progressBar.addWrong(correctAnswer);
                var wrongAnswer = $(this).attr("data-term");
                self.colorTerm(correctAnswer, "green");
                self.colorTerm(wrongAnswer, "red");
                $("#nextQuestionButton").show();
                self.practiceState = Practice.prototype.states.showing;
            }
        });


        $(".term").mouseenter(function() {
            var termId = $(this).attr("data-term") + "";
            window.console.log(termId);
            $(".term").each(function() {
                if ($(this).is("[data-term='" + termId + "']")
                        && $(this).is("[data-mouseover!='true']")
                        && $(this).is("[data-answer!='true']")) {
                    if ($(this).prop("tagName") == "polygon") {
                        var color = $(this).attr("data-color");
                        if (!color)
                            color = "rgb(222,222,222)";
                        $(this).attr("style", "opacity: 1; fill: " + color + ";");
                    } else {
                        $(this).attr("data-mouseover", "true");
                        var color = $(this).css("fill");
                        $(this).attr("data-color", color);
                        $(this).css("fill", "rgb(222,222,222)");
                    }
                }
            });
        });
        $(".term").mouseleave(function() {
            var termId = $(this).attr("data-term") + "";
            if (!termId)
                termId = $(this).parent().attr("data-term") + "";
            $("svg .term").each(function() {
                if ($(this).is("[data-term='" + termId + "']")
                        && $(this).is("[data-answer!='true']")) {
                    $(this).attr("data-mouseover", "false");
                    if ($(this).prop("tagName") == "polygon") {
                        $(this).attr("style", "");
                    } else {
                        var oldColor = $(this).attr("data-color");
                        if (oldColor.search("url") != -1) {
                            var kriz = oldColor.search("#");
                            id = oldColor.substr(kriz + 1);
                            id = id.split(")")[0];
                            window.console.log("back id " + id);
                            $("#" + id + " stop").each(function() {
                                var stopColor = $(this).css("stop-color", $(this).attr("data-color"));
                            });
                        } else {
                            $(this).css("fill", $(this).attr("data-color"));
                        }
                    }
                }
            });
        });

    };

    this.colorTerm = function(termId, color) {
        $("#pictureSvg svg .term").each(function() {
            if ($(this).is("[data-term='" + termId + "']")) {
                $(this).attr("data-answer", "true");
                if ($(this).prop("tagName") == "polygon") {
                    $(this).attr("style", "opacity: 1; fill: " + color + ";");
                } else {
                    if (!$(this).attr("data-color") || $(this).attr("data-color") == "") {
                        $(this).attr("data-color", $(this).css("fill"));
                    }
                    $(this).css("fill", color);
                }
            }
        });
    }

    this.showAfterQset = function() {
        self.practiceState = Practice.prototype.states.showing;
        $("#nextQuestionButton").hide();
        $("#questionText").html("");
        self.whiteBlack();
        self.progressBar.correctAnswers.forEach(function(entry) {
            self.colorTerm(entry, "green");
        });
        self.progressBar.wrongAnswers.forEach(function(entry) {
            self.colorTerm(entry, "red");
        });
        $("#newSetQButton").show();
    };

    this.newSetQ = function() {
        self.progressBar = new ProgressBar(5);
        self.loadNewQuestion();
    }

    this.clean = function() {
        $("#nextQuestionButton").hide();
        $("#newSetQButton").hide();
        $("#pictureName").html("");
        $("#pictureSvg").html("");
        $("#questionText").html("");
        $("#practiceBox").css("height", window.innerHeight - 70);
        $("#spinner").show();

    };
    this.loadNewQuestion = function() {
        if (self.progressBar.numQuestions == self.progressBar.numAnswer) {
            self.showAfterQset();
            return;
        }
        self.clean();
        self.practiceState = Practice.prototype.states.loadQuestion;
        $.get(self.url + "/getQuestion", function(data) {
            $("#spinner").hide();
            self.question = eval(data);
            $("#pictureName").html(" - " + self.question.pictureName);
            $("#pictureSvg").html(self.question.pictureSvg);
            //$("#pictureSvg").html('<svg id="svgTag" version="1.1" width="800" height="600"><g id="viewport"><ellipse cx="219" cy="205" rx="54" ry="55" fill="purple" stroke="yellow" stroke-width="3"></ellipse><circle cx="151" cy="172" r="61" fill="orange" stroke="green" stroke-width="3"></circle></g></svg>');

            svgRoot = null;
            var svg = $('#pictureSvg').find('svg')[0];

            svg.setAttribute('id', 'svgTag');

            $(window).resize(function() {
                window.console.log("w: " + window.innerWidth);
                window.console.log("h: " + window.innerHeight);
                var w = window.innerWidth;
                var h = window.innerHeight - 70;
                svg.setAttribute('width', w);
                svg.setAttribute('height', h);
                $("#practiceBox").css("height", h);
            }).resize();

            setAttributes(document.getElementById("svgTag"), {
                "onmouseup": "handleMouseUp(event)",
                "onmousedown": "handleMouseDown(event)",
                "onmousemove": "handleMouseMove(event)"//,
                        //"onmouseout": "handleMouseUp(event)" // Decomment this to stop the pan functionality when dragging out of the SVG element
            });

            if (navigator.userAgent.toLowerCase().indexOf('webkit') >= 0)
                window.addEventListener('mousewheel', handleMouseWheel, false); // Chrome/Safari
            else
                window.addEventListener('DOMMouseScroll', handleMouseWheel, false); // Others

            var addToQ = "";
            if (self.question.questionType.id == 1) {
                addToQ = '<span class="label label-default">' + self.question.question.termName + '</span>';
            }
            $("#questionText").html(self.question.questionType.textVar + addToQ);

            self.practiceState = Practice.prototype.states.waitForAnswerInMap;
            self.setEvents();
        })
                .fail(function() {
                    alert("Vyskytla se chzba při načítání nové otázky.");
                });
    };
}



Practice.prototype.states = {
    showing: 0,
    loadQuestion: 1,
    waitForAnswerInMap: 2,
    waitForAnswerWithButtons: 3};

function ProgressBar(numQuestions) {
    this.numQuestions = numQuestions;
    this.numAnswer = 0;
    this.numCorrect = 0;
    this.wrongAnswers = new Array();
    this.correctAnswers = new Array();
    this.addCorrect = function(correctAnswer) {
        this.numAnswer++;
        this.numCorrect++;
        this.setBar();
        this.correctAnswers.push(correctAnswer);
    }
    this.addWrong = function(correctAnswer) {
        this.numAnswer++;
        this.setBar();
        this.wrongAnswers.push(correctAnswer);
    }
    this.setBar = function() {
        $("#progressBar").css("width", ((this.numAnswer / this.numQuestions) * 100) + "%");
        $("#progressBar .sr-only").text(((this.numAnswer / this.numQuestions) * 100) + "%");
    }
    this.setBar();
}
