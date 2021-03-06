package com.tapestwitter.domain.business

import org.unitils.dbunit.annotation.DataSet
import org.unitils.spring.annotation.SpringApplicationContext
import org.unitils.spring.annotation.SpringBean

import spock.lang.Specification
import spock.lang.Unroll
import spock.unitils.UnitilsSupport

import com.tapestwitter.domain.model.User


@SpringApplicationContext("tapestwitterTest-config.xml")
@UnitilsSupport
@DataSet("TapestwitterBusinessTest.xml")
class DefaultAuthenticatorSpec extends Specification
{

    @SpringBean("authenticator")
    private Authenticator authenticator

    @Unroll("find a user by email #email")
    def "find a user by email"()
    {
        expect:
        User user = authenticator.findByEmail(email)
        user.email == email

        where:
        email << [
            'laurent@tapestwitter.org',
            'katia@tapestwitter.org',
            'loic@tapestwitter.org'
        ]
    }

    def "find a user by username"()
    {
        setup:
        def login = 'laurent'

        when:
        def user = authenticator.findByUsername(login)

        then:
        user.login == login
    }

    def "find a user with unknown username"()
    {
        setup:
        def login = 'lkjlkjlkj'

        when: "searching user by his username"
        def user = authenticator.findByUsername(login)

        then: "the return user is null"
        user == null
    }

    def "find a user with unknown email"()
    {
        setup:
        def unknownEmail = "aaa@tapestwitter.org"

        when: "searching user with unknown email adress"
        def user = authenticator.findByEmail(unknownEmail)

        then: "the return user is null"
        user == null
    }

    @Unroll("check if #name is already used")
    def "is username available"()
    {
        expect:
        authenticator.isAvailableName(name) == ok

        where:
        name       |ok
        'laurent'  |false
        'lauren'   |true
        'katia'    |false
        'tapestry' |true
        'loic'     |false
        'lo�c'     |true
    }

    @Unroll("check if #email is already used")
    def "is email available"()
    {
        expect:
        authenticator.isAvailableEmail(email) == ok

        where:
        email                     |ok
        'laurent@tapestwitter.org'|false
        'loic@tapestwitter.org'   |false
        'karesti@gmail.com'       |true
    }

    def "create a new user"()
    {
        setup: "a great new tapestwitter user"
        def login = 'tapestry'
        def user = new User(login: login, email: "tapestry@tapestwitter.org", fullName: "Tapestry rocks !", password: "tapestrypass")

        when: "it is persisted into database"
        authenticator.createUser(user)

        then: "id field is well generated"
        user.id != null

        and: "other fields are ok"
        def createdUser = authenticator.findByUsername(login)
        createdUser.email == user.email
        createdUser.fullName == user.fullName
        createdUser.password == user.password
    }
}
