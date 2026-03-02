package com.louisgautier.domain

import com.louisgautier.domain.model.CharacterFrequencyLevel
import com.louisgautier.domain.model.Dictionary
import com.louisgautier.domain.model.DictionaryWithGraphic
import com.louisgautier.domain.model.Difficulty
import com.louisgautier.domain.model.Graphic
import com.louisgautier.domain.model.Point
import com.louisgautier.domain.model.Session
import com.louisgautier.domain.model.SimpleDictionary
import com.louisgautier.domain.model.Statistics
import com.louisgautier.domain.model.Stroke
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
val previewSession = Session(
    id = 1,
    date = Clock.System.now(),
    duration = 15.seconds,
    difficulty = Difficulty.EASY,
    responses = emptyList(),
    score = 250
)

val previewStatistics = Statistics(
    totalScore = 3000,
    averageTime = 2.seconds,
    averageDifficulty = Difficulty.EASY,
    currentDayStreak = 123,
    sessionCount = 10
)

val previewDictionary = Dictionary(
    code = '超'.code,
    definition = "definition",
    pinyin = listOf("chāo"),
    decomposition = "",
    decompositionList = emptyList(),
    level = CharacterFrequencyLevel.FREQUENT,
    etymology = null,
    radical = null,
    matches = emptyList()
)

val previewSimpleDictionary = SimpleDictionary(
    code = '超'.code,
    pinyin = listOf("chāo"),
    level = CharacterFrequencyLevel.FREQUENT
)
val previewGraphic = Graphic(
    code = '超'.code,
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
        " M 610 182 Q 619 181 630 183 Q 685 195 807 204 Q 817 205 820 214 Q 820 221 797 237 C 777 251 777 251 753 248 Q 744 247 738 245 Q 663 227 606 218 C 576 213 580 183 610 182 Z"

    ),
    medians = listOf(
        listOf(listOf(213, 606), listOf(271, 601), listOf(438, 639), listOf(489, 639)),
        listOf(listOf(326, 813), listOf(372, 773), listOf(362, 529), listOf(345, 503)),
        listOf(
            listOf(87, 434),
            listOf(148, 427),
            listOf(418, 494),
            listOf(465, 502),
            listOf(512, 499)
        ),
        listOf(listOf(339, 449), listOf(369, 422), listOf(368, 234), listOf(387, 205)),
        listOf(listOf(401, 330), listOf(424, 352), listOf(456, 356), listOf(483, 351)),
        listOf(
            listOf(220, 375),
            listOf(245, 338),
            listOf(195, 226),
            listOf(167, 183),
            listOf(109, 116),
            listOf(55, 75)
        ),
        listOf(
            listOf(257, 271),
            listOf(260, 259),
            listOf(295, 232),
            listOf(467, 124),
            listOf(561, 75),
            listOf(664, 32),
            listOf(725, 15),
            listOf(750, 15),
            listOf(947, 41)
        ),
        listOf(
            listOf(547, 669),
            listOf(599, 664),
            listOf(777, 705),
            listOf(793, 705),
            listOf(815, 687),
            listOf(797, 554),
            listOf(770, 494),
            listOf(745, 470),
            listOf(668, 508)
        ),
        listOf(
            listOf(627, 651),
            listOf(651, 631),
            listOf(655, 618),
            listOf(639, 576),
            listOf(582, 479),
            listOf(526, 422)
        ),
        listOf(listOf(536, 359), listOf(566, 324), listOf(597, 159)),
        listOf(
            listOf(586, 358),
            listOf(594, 350),
            listOf(614, 350),
            listOf(729, 374),
            listOf(760, 386),
            listOf(791, 378),
            listOf(812, 356),
            listOf(780, 263),
            listOf(763, 257)
        ),
        listOf(listOf(615, 189), listOf(626, 202), listOf(750, 224), listOf(812, 214))
    ).map { s -> Stroke(s.map { p -> Point(p[0].toFloat(), p[1].toFloat()) }) }
)

val previewDictionaryWithGraphic = DictionaryWithGraphic(
    dictionary = previewDictionary,
    graphics = previewGraphic
)

val previewSimpleDataList
    get() = (('a'..'z') + ('A'..'Z'))
        .map { previewSimpleDictionary.copy(code = it.code) }

