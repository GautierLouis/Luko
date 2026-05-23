package xyz.luko.baseui.preview

import xyz.luko.domain.model.CharacterFrequencyLevel
import xyz.luko.domain.model.Decomposition
import xyz.luko.domain.model.Dictionary
import xyz.luko.domain.model.DifficultyLevel
import xyz.luko.domain.model.Point
import xyz.luko.domain.model.Session
import xyz.luko.domain.model.SimpleDictionary
import xyz.luko.domain.model.Statistics
import xyz.luko.domain.model.Stroke
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds

object PreviewProvider {

    val dictionary: Dictionary by lazy {
        getFakeDictionary()
    }

    val simpleDictionary by lazy {
        SimpleDictionary(
            code = dictionary.code,
            pinyin = dictionary.pinyin,
            level = dictionary.level,
        )
    }

    val simpleDictionaryList by lazy {
        getCharacterList().map { (code, pinyin) ->
            SimpleDictionary(
                code = code,
                pinyin = listOf(pinyin),
            )
        }
    }

    val session by lazy {
        Session(
            id = 1,
            date = Clock.System.now(),
            duration = 15.seconds,
            difficulty = DifficultyLevel.EASY,
            questionsCount = 5,
            score = 250,
        )
    }

    val statistics by lazy {
        Statistics(
            totalScore = 3000,
            averageTime = 2.seconds,
            averageDifficulty = DifficultyLevel.EASY,
            currentDayStreak = 123,
            sessionCount = 10,
        )
    }


    private fun getCharacterList() = mapOf(
        20054 to "guāi",
        20056 to "chéng",
        20060 to "miē",
        20061 to "jiǔ",
        20062 to "qǐ",
        20063 to "yě",
        20070 to "shū",
        20073 to "jī",
        20080 to "mǎi",
        20081 to "luàn",
    )

    private fun getFakeDictionary() = Dictionary(

        code = 36229,
        pinyin = listOf("chāo"),
        decomposition = listOf(
            Decomposition(symbolCode = 12282, glyphsCode = listOf(36208, 21484))
        ),
        level = CharacterFrequencyLevel.COMMON,
        strokes = listOf(
            "M 391 602 Q 491 626 495 629 Q 504 636 499 645 Q 492 655 464 663 Q 440 667 395 653 L 339 636 Q 330 635 324 632 Q 276 622 221 616 Q 185 610 211 594 Q 248 572 316 587 Q 326 590 340 590 L 391 602 Z",
            " M 382 506 Q 386 555 391 602 L 395 653 Q 396 714 415 778 Q 418 788 398 804 Q 362 823 338 827 Q 322 831 314 822 Q 307 815 315 800 Q 334 773 334 754 Q 338 699 339 636 L 340 590 Q 340 545 339 495 C 338 465 380 476 382 506 Z",
            " M 369 460 Q 430 475 504 486 Q 522 489 522 496 Q 523 509 504 518 Q 471 531 382 506 L 339 495 Q 168 458 93 448 Q 80 447 76 438 Q 73 428 89 416 Q 131 388 173 403 Q 318 454 332 450 L 369 460 Z",
            " M 389 196 Q 392 265 394 323 L 396 366 Q 402 430 397 437 Q 384 450 369 460 C 344 477 322 478 332 450 Q 341 431 345 351 Q 345 305 347 221 C 348 191 388 166 389 196 Z",
            " M 394 323 Q 442 333 479 338 Q 506 345 494 359 Q 481 375 450 377 Q 428 378 396 366 C 368 356 365 317 394 323 Z",
            " M 255 282 Q 271 309 287 331 Q 297 344 283 358 Q 240 391 215 388 Q 205 385 206 371 Q 218 266 96 130 Q 86 126 45 76 Q 38 61 52 66 Q 112 75 203 197 Q 221 224 239 255 L 255 282 Z",
            " M 239 255 Q 333 176 494 80 Q 680 -34 735 -32 Q 783 -25 917 16 Q 948 26 957 38 Q 961 50 942 50 Q 845 57 786 57 Q 626 60 409 185 Q 399 191 389 196 L 347 221 Q 301 249 255 282 C 230 299 216 274 239 255 Z",
            " M 655 659 Q 713 675 761 682 Q 777 683 779 679 Q 786 669 783 641 Q 777 563 759 529 Q 743 499 716 503 Q 694 507 674 514 Q 652 520 668 499 Q 707 463 725 436 Q 740 418 755 432 Q 801 465 824 557 Q 839 653 857 679 Q 869 691 863 700 Q 854 710 807 731 Q 788 740 771 727 Q 753 717 685 701 Q 616 683 558 679 Q 542 678 540 670 Q 539 663 560 652 Q 582 639 600 645 Q 610 649 621 651 L 655 659 Z",
            " M 621 651 Q 622 650 624 646 Q 642 586 524 428 Q 520 424 520 418 Q 520 414 525 415 Q 585 430 663 576 Q 663 583 683 612 Q 687 622 681 632 Q 668 651 655 659 C 631 677 608 678 621 651 Z",
            " M 577 359 Q 567 363 542 369 Q 530 372 527 367 Q 520 361 528 346 Q 553 292 567 204 Q 570 177 585 159 Q 601 138 607 153 Q 611 165 610 182 L 606 218 Q 593 306 593 331 C 592 354 592 354 577 359 Z",
            " M 797 237 Q 824 318 855 341 Q 871 357 857 374 Q 785 428 735 398 Q 668 377 577 359 C 548 353 563 327 593 331 Q 603 331 746 362 Q 762 366 769 359 Q 782 344 753 248 C 744 219 787 209 797 237 Z",
            " M 610 182 Q 619 181 630 183 Q 685 195 807 204 Q 817 205 820 214 Q 820 221 797 237 C 777 251 777 251 753 248 Q 744 247 738 245 Q 663 227 606 218 C 576 213 580 183 610 182 Z",
        ),
        medians = listOf(
            Stroke(
                listOf(
                    Point.Straight(208.00781f, 591.7969f),
                    Point.Straight(477.53906f, 624.02344f),
                )
            ),
            Stroke(
                listOf(
                    Point.Straight(318.35938f, 793.9453f),
                    Point.Curved(
                        363.28125f, 754.8828f,
                        322.85156f, 790.03906f, 361.42578f, 785.15625f
                    ),
                    Point.Curved(
                        336.91406f,
                        491.21094f,
                        365.13672f, 724.6094f, 339.55078f, 517.5781f
                    ),
                )
            ),
            Stroke(
                listOf(
                    Point.Straight(84.96094f, 423.82812f),
                    Point.Curved(
                        144.53125f,
                        416.9922f,
                        90.91797f, 423.14453f, 103.02734f, 410.64453f
                    ),
                    Point.Curved(
                        500.0f,
                        487.3047f,
                        186.03516f, 423.33984f, 464.45312f, 480.27344f
                    ),
                )
            ),
            Stroke(
                listOf(
                    Point.Straight(331.0547f, 438.47656f),
                    Point.Curved(
                        360.35156f,
                        412.10938f,
                        333.98438f, 435.83984f, 357.51953f, 433.10547f
                    ),
                    Point.Curved(
                        359.375f,
                        228.51562f,
                        363.1836f, 391.11328f, 357.6172f, 249.70703f
                    ),
                    Point.Curved(
                        377.9297f,
                        200.19531f,
                        361.1328f, 207.32422f, 376.07422f, 203.02734f
                    ),
                )
            ),
            Stroke(
                listOf(
                    Point.Straight(391.60156f, 322.26562f),
                    Point.Curved(
                        414.0625f,
                        343.75f,
                        393.84766f, 324.41406f, 406.0547f, 341.69922f
                    ),
                    Point.Curved(
                        471.6797f,
                        342.77344f,
                        422.0703f, 345.80078f, 465.91797f, 342.8711f
                    ),
                )
            ),
            Stroke(
                listOf(
                    Point.Straight(214.84375f, 366.21094f),
                    Point.Curved(
                        239.25781f,
                        330.07812f,
                        217.28516f, 362.59766f, 241.69922f, 344.6289f
                    ),
                    Point.Curved(
                        190.42969f,
                        220.70312f,
                        236.8164f, 315.52734f, 208.98438f, 246.38672f
                    ),
                    Point.Curved(
                        53.710938f,
                        73.24219f,
                        171.875f, 195.01953f, 67.38281f, 87.98828f
                    ),
                )
            ),
            Stroke(
                listOf(
                    Point.Straight(250.97656f, 264.64844f),
                    Point.Curved(
                        456.0547f,
                        121.09375f,
                        71.48438f, 250.29297f, 416.3086f, 144.4336f
                    ),
                    Point.Curved(
                        648.4375f,
                        31.25f,
                        495.80078f, 97.75391f, 620.8008f, 41.89453f
                    ),
                    Point.Curved(
                        732.4219f,
                        14.6484375f,
                        676.0742f, 20.605469f, 704.78516f, 13.769531f
                    ),
                    Point.Curved(
                        924.8047f,
                        40.039062f,
                        760.0586f, 15.527344f, 905.5664f, 37.5f
                    ),
                )
            ),
            Stroke(
                listOf(
                    Point.Straight(534.1797f, 653.3203f),
                    Point.Curved(
                        758.78906f,
                        688.47656f,
                        556.6406f, 656.83594f, 732.6172f, 686.71875f
                    ),
                    Point.Curved(
                        795.89844f,
                        670.89844f,
                        784.96094f, 690.2344f, 796.58203f, 691.5039f
                    ),
                    Point.Curved(
                        751.9531f,
                        482.42188f,
                        795.21484f, 650.29297f, 758.78906f, 503.61328f
                    ),
                    Point.Curved(
                        727.53906f,
                        458.98438f,
                        745.1172f, 461.23047f, 737.5f, 457.6172f
                    ),
                    Point.Curved(
                        652.34375f,
                        496.09375f,
                        717.5781f, 460.35156f, 659.8633f, 492.3828f
                    ),
                )
            ),
            Stroke(
                listOf(
                    Point.Straight(612.3047f, 635.7422f),
                    Point.Curved(
                        639.64844f,
                        603.5156f,
                        615.03906f, 632.51953f, 638.47656f, 610.83984f
                    ),
                    Point.Curved(
                        624.02344f,
                        562.5f,
                        640.8203f, 596.1914f, 636.6211f, 581.6406f
                    ),
                    Point.Curved(
                        513.6719f,
                        412.10938f,
                        611.4258f, 543.3594f, 524.70703f, 427.14844f
                    ),
                )
            ),
            Stroke(
                listOf(
                    Point.Straight(523.4375f, 350.58594f),
                    Point.Curved(
                        552.7344f,
                        316.40625f,
                        526.3672f, 347.16797f, 546.77734f, 335.9375f
                    ),
                    Point.Curved(
                        583.0078f,
                        155.27344f,
                        558.6914f, 296.875f, 579.98047f, 171.38672f
                    ),
                )
            ),
            Stroke(
                listOf(
                    Point.Straight(572.2656f, 349.60938f),
                    Point.Curved(
                        772.46094f,
                        369.14062f,
                        592.28516f, 351.5625f, 750.3906f, 369.33594f
                    ),
                    Point.Curved(
                        792.96875f,
                        347.65625f,
                        794.53125f, 368.9453f, 795.7031f, 359.47266f
                    ),
                    Point.Curved(
                        745.1172f,
                        250.97656f,
                        790.2344f, 335.83984f, 749.90234f, 260.64453f
                    ),
                )
            ),
            Stroke(
                listOf(
                    Point.Straight(600.58594f, 184.57031f),
                    Point.Curved(
                        732.4219f,
                        218.75f,
                        613.76953f, 187.98828f, 713.1836f, 216.3086f
                    ),
                    Point.Curved(
                        792.96875f,
                        208.98438f,
                        751.66016f, 221.1914f, 786.91406f, 209.96094f
                    ),
                )
            ),
        ),
    )
}
