/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CaptainsLogAppTestModule } from '../../../test.module';
import { ELRDetailComponent } from 'app/entities/elr/elr-detail.component';
import { ELR } from 'app/shared/model/elr.model';

describe('Component Tests', () => {
    describe('ELR Management Detail Component', () => {
        let comp: ELRDetailComponent;
        let fixture: ComponentFixture<ELRDetailComponent>;
        const route = ({ data: of({ eLR: new ELR(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaptainsLogAppTestModule],
                declarations: [ELRDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ELRDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ELRDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.eLR).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
