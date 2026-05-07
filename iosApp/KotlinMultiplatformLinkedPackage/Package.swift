// swift-tools-version: 5.9

import PackageDescription

let package = Package(
    name: "KotlinMultiplatformLinkedPackage",
    platforms: [
        .iOS("15.0")
    ],
    products: [
        .library(
            name: "KotlinMultiplatformLinkedPackage",
            type: .none,
            targets: ["KotlinMultiplatformLinkedPackage"]
        )
    ],
    dependencies: [
        .package(path: "subpackages/_library_core_firebase")
    ],
    targets: [
        .target(
            name: "KotlinMultiplatformLinkedPackage",
            dependencies: [
                .product(name: "_library_core_firebase", package: "_library_core_firebase")
            ]
        )
    ]
)
