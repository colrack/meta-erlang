include erlang.inc
include erlang-20.2.1.inc

inherit nativesdk

require erlang-${PV}-manifest.inc

DEPENDS = "erlang-native openssl ncurses"

RDEPENDS_${PN} = "nativesdk-ncurses nativesdk-erlang-modules"

EXTRA_OECONF = "--with-ssl=${STAGING_DIR_NATIVE}"

PR = "r0"

# CACHED_CONFIGUREVARS += "ac_cv_header_netinet_sctp_h=yes ac_cv_header_netinet_sctp_uio_h=yes ac_cv_sctp=yes ac_cv_prog_javac_ver_1_2=no ac_cv_prog_javac_ver_1_5=no erl_xcomp_sysroot=${STAGING_DIR_NATIVE}"
CACHED_CONFIGUREVARS += "ac_cv_prog_javac_ver_1_2=no ac_cv_prog_javac_ver_1_5=no erl_xcomp_sysroot=${STAGING_DIR_NATIVE}"

do_configure() {

    cd ${S}; ./otp_build autoconf

    TARGET=${HOST_SYS} \
    erl_xcomp_sysroot=${STAGING_DIR_HOST}${SDKPATHNATIVE} \
    oe_runconf
}

do_compile_prepend() {
    export TARGET=${HOST_SYS}
}

do_install_prepend() {
    export TARGET=${HOST_SYS}
}

do_install_append() {
    rm -f ${D}/${libdir}/erlang/Install
}
